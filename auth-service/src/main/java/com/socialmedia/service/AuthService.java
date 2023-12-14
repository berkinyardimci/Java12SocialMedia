package com.socialmedia.service;

import com.socialmedia.dto.request.ActivateCodeRequest;
import com.socialmedia.dto.request.LoginRequestDto;
import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.request.UpdateRequestDto;
import com.socialmedia.dto.response.RegisterResponse;
import com.socialmedia.entity.Auth;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.excepiton.AuthManagerException;
import com.socialmedia.excepiton.ErrorType;
import com.socialmedia.manager.IUserManager;
import com.socialmedia.mapper.IAuthMapper;
import com.socialmedia.rabbitmq.producer.ActiveStatusProducer;
import com.socialmedia.rabbitmq.producer.MailSenderProducer;
import com.socialmedia.rabbitmq.producer.RegisterProducer;
import com.socialmedia.repository.IAuthRepository;
import com.socialmedia.util.CodeGenerator;
import com.socialmedia.util.JWTTokenManager;
import com.socialmedia.util.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository authRepository;
    private final JWTTokenManager tokenManager;
    private final IUserManager userManager;
    private final RegisterProducer registerProducer;
    private final ActiveStatusProducer activeStatusProducer;
    private final MailSenderProducer mailSenderProducer;
    private final CacheManager cacheManager;

    public AuthService(IAuthRepository authRepository, JWTTokenManager tokenManager, IUserManager userManager,
                       RegisterProducer registerProducer, ActiveStatusProducer activeStatusProducer,
                       MailSenderProducer mailSenderProducer, CacheManager cacheManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.tokenManager = tokenManager;
        this.userManager = userManager;
        this.registerProducer = registerProducer;
        this.activeStatusProducer = activeStatusProducer;
        this.mailSenderProducer = mailSenderProducer;
        this.cacheManager = cacheManager;
    }

    @Transactional
    public RegisterResponse register(RegisterRequestDto request) {
        try {
            if (authRepository.existsByEmail(request.getEmail())) {
                throw new AuthManagerException(ErrorType.EMAIL_EXITS);
            }
            Auth auth = Auth.builder()
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .username(request.getUsername())
                    .activationCode(CodeGenerator.generateCode())
                    .build();
            authRepository.save(auth);
            userManager.createNewUser(IAuthMapper.INSTANCE.toUserSaveRequestDto(auth));
            return IAuthMapper.INSTANCE.toRegisterResponse(auth);
        } catch (Exception e) {
            throw new AuthManagerException(ErrorType.INTERNAL_ERROR_SERVER);
        }
    }

    @Transactional
    public RegisterResponse registerWithRabbit(RegisterRequestDto request) {
        try {
            if (authRepository.existsByEmail(request.getEmail())) {
                throw new AuthManagerException(ErrorType.EMAIL_EXITS);
            }
            Auth auth = Auth.builder()
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .username(request.getUsername())
                    .activationCode(CodeGenerator.generateCode())
                    .build();
            authRepository.save(auth);
            mailSenderProducer.convertAndSendToRabbit(IAuthMapper.INSTANCE.toMailSenderModel(auth));
            registerProducer.sendNewUser(IAuthMapper.INSTANCE.toRegisterModel(auth));
            return IAuthMapper.INSTANCE.toRegisterResponse(auth);
        } catch (Exception e) {


            System.out.println(e);
            throw new AuthManagerException(ErrorType.INTERNAL_ERROR_SERVER);

        }
    }

    public String login(LoginRequestDto request) {
        Optional<Auth> optionalAuth = authRepository
                .findOptionalByEmailAndPassword(request.getEmail(), request.getPassword());
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }

        if (!optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }

        Optional<String> token = tokenManager.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole());
        if (token.isEmpty()) {
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }
        //return IAuthMapper.INSTANCE.toLoginResponse(optionalAuth.get());
        return token.get();
    }
    @CacheEvict(cacheNames = "findbystatus",allEntries = true)
    public String activateCode(ActivateCodeRequest request) {
        Optional<Auth> optionalAuth = findById(request.getId());
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!optionalAuth.get().getActivationCode().equals(request.getActivationCode())) {
            throw new AuthManagerException(ErrorType.ACTIVATION_CODE_MISMATCH);
        }
        //cacheManager.getCache("findByStatus").evict(optionalAuth.get().getStatus());
        //Anatasyon ile çözdük

        return statusControl(optionalAuth.get());
    }

    //SQL injection

    //username: mehmet123____________
    //password: 123456' limit '1@ ofset '2'___________
    //select * from user username=mehmet123 and passowr=123456 or 1=1

    private String statusControl(Auth auth) {
        switch (auth.getStatus()) {
            case ACTIVE -> {
                return "Hesap Zaten Aktif";
            }
            case PENDING -> {
                auth.setStatus(EStatus.ACTIVE);
                update(auth);
                //userManager.activateUser(auth.getId());
                activeStatusProducer.convertAndSendToRabbit(auth.getId());
                return "Aktivasyon Başarılı";
            }
            case BANNED -> {
                return "Hesanınız Engellenmiştir";
            }
            case DELETED -> {
                return "Hesabınız Silimiştir";
            }
            default -> {
                throw new AuthManagerException(ErrorType.INTERNAL_ERROR_SERVER);
            }
        }
    }

    public String softDelete(Long id) {
        Optional<Auth> optionalAuth = findById(id);
        if (optionalAuth.isEmpty()) {

            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!optionalAuth.get().getStatus().equals(EStatus.DELETED)) {
            optionalAuth.get().setStatus(EStatus.DELETED);
            save(optionalAuth.get());
            return id + " idli Kullanıcı Silindi";
        } else {
            throw new AuthManagerException(ErrorType.USER_ALREADY_DELETED);
        }
    }

    public String updateAuth(UpdateRequestDto dto) {
        Optional<Auth> optionalAuth = findById(dto.getId());
        Auth auth = optionalAuth.orElseThrow(() -> new AuthManagerException(ErrorType.USER_NOT_FOUND));

        if (authRepository.existsByEmail(dto.getEmail())) {
            throw new AuthManagerException(ErrorType.EMAIL_EXITS);
        }
        auth.setEmail(dto.getEmail());
        update(auth);
        return "Günceleme Başarılı.....";

    }
}
