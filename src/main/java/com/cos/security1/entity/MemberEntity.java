package com.cos.security1.entity;

import com.cos.security1.dto.MemberDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "member")
public class MemberEntity extends BaseEntity{
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String role; // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN

    // oauth2를 위한 필드 추가
    private String provider;    // google. facebook 등등
    private String prividerId;  // sub값(pk)

    public static MemberEntity toJoinEntity(MemberDto memberDto){
        return MemberEntity.builder()
                .username(memberDto.getUsername())
                .password(memberDto.getPassword())
                .email(memberDto.getEmail())
                .role(memberDto.getRole())
                .build();
    }

}
