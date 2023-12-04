package com.socialmedia.entity;

import com.socialmedia.entity.enums.ERole;
import com.socialmedia.entity.enums.EStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String activationCode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ERole role = ERole.USER;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EStatus status = EStatus.PENDING;


}
