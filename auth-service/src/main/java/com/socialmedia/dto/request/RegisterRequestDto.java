package com.socialmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    private String username;
    private String email;
    private String password;
    private String rePassword;
}
