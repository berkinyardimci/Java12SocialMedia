package com.socialmedia.controller;



/*
       - ilk önce gerekli paketlerimizi oluşturalım +
       - password, email, username, role(ADMIN USER, Default USER), activationCode, Status +
       - Activate, Deleted, Pending, Banned (Default Pending) +
       - Exception Yapısnı oluşturalım +
       - 550e8400-e29b-41d4-a716-446655440000
       - 5e4a4

       - Register metodu yazalım
       - Auth dönsün, request --> username, password , email.


       - activeStatus metodu yazcaz
 */



import com.socialmedia.dto.request.ActivateCodeRequest;
import com.socialmedia.dto.request.LoginRequestDto;
import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.response.LoginResponse;
import com.socialmedia.entity.Auth;
import com.socialmedia.excepiton.AuthManagerException;
import com.socialmedia.excepiton.ErrorType;
import com.socialmedia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Auth> register(@RequestBody @Valid RegisterRequestDto request) {
            if (!request.getPassword().equals(request.getRePassword())) {
                throw new AuthManagerException(ErrorType.PASSWORD_MISMATCH);
            }
            return ResponseEntity.ok(authService.register(request));

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/activateCode")
    public ResponseEntity<String> activateCode(@RequestBody ActivateCodeRequest request) {
        return ResponseEntity.ok(authService.activateCode(request));
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long authId) {
        return ResponseEntity.ok(authService.softDelete(authId));
    }

}