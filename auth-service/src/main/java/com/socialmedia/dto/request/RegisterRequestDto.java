package com.socialmedia.dto.request;

import com.socialmedia.constant.ErrorStaticMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @NotNull
    @Size(min = 2, max = 20,message = ErrorStaticMessage.USERNAME_NOT_VALID)
    @NotBlank(message = "Kullanıcı Adı Boş Geçilemez")
    private String username;

    @Email
    private String email;

    @NotNull
    @Size(min = 2, max = 20,message = "Password adı min 2 max 20 olabilir")
    @NotBlank(message = "Password Adı Boş Geçilemez")
    private String password;

    private String rePassword;
}
