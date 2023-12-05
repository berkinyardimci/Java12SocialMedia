package com.socialmedia.service;

import com.socialmedia.dto.request.ActivateCodeRequest;
import com.socialmedia.dto.request.LoginRequestDto;
import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.response.RegisterResponse;
import com.socialmedia.entity.Auth;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.excepiton.AuthManagerException;
import com.socialmedia.excepiton.ErrorType;
import com.socialmedia.manager.IUserManager;
import com.socialmedia.mapper.IAuthMapper;
import com.socialmedia.repository.IAuthRepository;
import com.socialmedia.util.CodeGenerator;
import com.socialmedia.util.JWTTokenManager;
import com.socialmedia.util.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;
    private final JWTTokenManager tokenManager;
    private final IUserManager userManager;

    public AuthService(IAuthRepository authRepository, JWTTokenManager tokenManager, IUserManager userManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.tokenManager = tokenManager;
        this.userManager = userManager;
    }

    @Transactional
    public RegisterResponse register(RegisterRequestDto request) {
        try {
            if(authRepository.existsByEmail(request.getEmail())){
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
        }catch (Exception e){
            throw new AuthManagerException(ErrorType.INTERNAL_ERROR_SERVER);
        }
    }

    public String login(LoginRequestDto request) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmailAndPassword(request.getEmail(), request.getPassword());
        if(optionalAuth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }

        if(!optionalAuth.get().getStatus().equals(EStatus.ACTIVE)){
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }

        Optional<String> token = tokenManager.createToken(optionalAuth.get().getId(),optionalAuth.get().getRole());
        if(token.isEmpty()){
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }
        //return IAuthMapper.INSTANCE.toLoginResponse(optionalAuth.get());
        return token.get();
    }

    public String activateCode(ActivateCodeRequest request) {
        Optional<Auth> optionalAuth = findById(request.getId());
        if(optionalAuth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if(!optionalAuth.get().getActivationCode().equals(request.getActivationCode())){
            throw new AuthManagerException(ErrorType.ACTIVATION_CODE_MISMATCH);
        }
        return statusControl(optionalAuth.get());
    }

    private String statusControl(Auth auth){
        switch (auth.getStatus()){
            case ACTIVE -> {
                return "Hesap Zaten Aktif";
            }
            case PENDING -> {
                auth.setStatus(EStatus.ACTIVE);
                update(auth);
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

    public String softDelete(Long id){
        Optional<Auth> optionalAuth = findById(id);
        if(optionalAuth.isEmpty()){

            //Todo: USER_NOT_FOUND mesajı değişcek
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if(!optionalAuth.get().getStatus().equals(EStatus.DELETED)){
            optionalAuth.get().setStatus(EStatus.DELETED);
            save(optionalAuth.get());
            return id + " idli Kullanıcı Silindi";
        }else {
            throw new AuthManagerException(ErrorType.USER_ALREADY_DELETED);
        }
    }

    /*
    public String activeStatus(ActivateCodeRequest dto) {
        Optional<Auth> optionalAuth = authRepository.findByIdAndActivationCode(dto.getId(), dto.getActivationCode());
        if (optionalAuth.isPresent()){
            if (optionalAuth.get().getStatus().equals(EStatus.PENDING)){
                optionalAuth.get().setStatus(EStatus.ACTIVE);
                authRepository.save(optionalAuth.get());
                return "Aktivasyon başarılı";
            }
            //mesaj kısmı dinamik geliyor gelen
            //optionalAuth.get().getStatus().name() satırından kullanıcın satutusune göre hata mesajı değişiyor.
            throw new AuthServiceException(ErrorType.STATUS_NOT_SUITABLE, ErrorType.STATUS_NOT_SUITABLE.getMessage() + optionalAuth.get().getStatus().name());
        }
        throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
    }
     */

}
