package com.socialmedia.service;

import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.entity.Auth;
import com.socialmedia.excepiton.AuthManagerException;
import com.socialmedia.excepiton.ErrorType;
import com.socialmedia.repository.IAuthRepository;
import com.socialmedia.util.CodeGenerator;
import com.socialmedia.util.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;

    public AuthService(IAuthRepository authRepository) {
        super(authRepository);
        this.authRepository = authRepository;
    }

    public Auth register(RegisterRequestDto request) {

        if(authRepository.existsByEmail(request.getEmail())){
            throw new AuthManagerException(ErrorType.EMAIL_EXITS);
        }
        Auth auth = Auth.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .username(request.getUsername())
                .activationCode(CodeGenerator.generateCode())
                .build();
        return authRepository.save(auth);
    }
}
