package com.socialmedia.excepiton;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(5100, "Sunucu Hatası",HttpStatus.INTERNAL_SERVER_ERROR),

    EMAIL_EXITS(2100, "Email Kullanılıyor",HttpStatus.BAD_REQUEST),

    PASSWORD_MISMATCH(2200, "Şifreler Uyuşmuyor",HttpStatus.BAD_REQUEST),

    PARAMETER_NOT_VALID(5000,"Parametre Hatası",HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(3000,"Email ve ya Şifre hatalı veya Eksik",HttpStatus.NOT_FOUND),

    ACCOUNT_NOT_ACTIVE(3001,"Hesabınız Aktif Değil" ,HttpStatus.FORBIDDEN),

    ACTIVATION_CODE_MISMATCH(3002,"Aktivasyon Kodu Hatalı" , HttpStatus.BAD_REQUEST),

    USER_ALREADY_DELETED(3003,"Hesap Zaten Dilinmiş" ,HttpStatus.BAD_REQUEST ),

    INVALID_TOKEN(6000, "Geçersiz Token ", HttpStatus.BAD_REQUEST),

    TOKEN_NOT_CREATED(6001,"Token Oluşturulamadı", HttpStatus.BAD_REQUEST);



    private int code;
    private String message;
    HttpStatus httpStatus;

}
