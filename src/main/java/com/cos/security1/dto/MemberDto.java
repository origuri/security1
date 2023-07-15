package com.cos.security1.dto;

import lombok.*;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role; // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
}
