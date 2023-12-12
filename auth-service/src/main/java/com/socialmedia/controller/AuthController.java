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
import com.socialmedia.dto.request.UpdateRequestDto;
import com.socialmedia.dto.response.LoginResponse;
import com.socialmedia.dto.response.RegisterResponse;
import com.socialmedia.entity.Auth;
import com.socialmedia.entity.enums.ERole;
import com.socialmedia.excepiton.AuthManagerException;
import com.socialmedia.excepiton.ErrorType;
import com.socialmedia.service.AuthService;
import com.socialmedia.util.JWTTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final JWTTokenManager tokenManager;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequestDto request) {
            if (!request.getPassword().equals(request.getRePassword())) {
                throw new AuthManagerException(ErrorType.PASSWORD_MISMATCH);
            }
            return ResponseEntity.ok(authService.register(request));

    }

    @PostMapping("/registerRabbit")
    public ResponseEntity<RegisterResponse> registerRabbit(@RequestBody @Valid RegisterRequestDto request) {
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new AuthManagerException(ErrorType.PASSWORD_MISMATCH);
        }
        return ResponseEntity.ok(authService.registerWithRabbit(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
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

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody @Valid UpdateRequestDto dto){
        return  ResponseEntity.ok(authService.updateAuth(dto));
    }

    @GetMapping("/getToken")
    public ResponseEntity<String> getToken(Long id){
        return ResponseEntity.ok(tokenManager.createToken(id).get());
    }

    @GetMapping("/getIdfromtoken")
    public ResponseEntity<Long> getIdFromToken(String token){
        return ResponseEntity.ok(tokenManager.getIdFromToken(token).get());
    }

    @GetMapping("/getRolefromtoken")
    public ResponseEntity<String> getRoleFromToken(String token){
        return ResponseEntity.ok(tokenManager.getRoleFromToken(token).get());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Auth>> findAll(){
        return  ResponseEntity.ok(authService.findAll());
    }
}