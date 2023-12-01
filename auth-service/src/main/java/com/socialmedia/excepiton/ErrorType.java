package com.socialmedia.excepiton;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(5100, "Sunucu Hatası",HttpStatus.INTERNAL_SERVER_ERROR),

    EMAIL_EXITS(2100, "Email Kullanılıyor",HttpStatus.BAD_REQUEST),

    PASSWORD_MISMATCH(2200, "Şifreler Uyuşmuyor",HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    HttpStatus httpStatus;

}
