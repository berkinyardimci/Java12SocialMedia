package com.socialmedia.dto.response;

import com.socialmedia.entity.enums.ERole;
import com.socialmedia.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String username;
    private String email;
    private ERole role;
    private EStatus status;
}
