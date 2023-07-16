package com.cos.security1.service;

import com.cos.security1.dto.MemberDto;
import com.cos.security1.entity.MemberEntity;
import com.cos.security1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(MemberDto memberDto) {
        // 입력한 비밀번호를 가져옴
        String rawPassword = memberDto.getPassword();
        // 해시 암호화 실행
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        // 해시 암호화한 비밀번호를 dto에 setting
        memberDto.setPassword(encPassword);
        // @bulid를 사용하여 memberDto를 Entity로 변환한다.
        MemberEntity memberEntity = MemberEntity.toJoinEntity(memberDto);
        // JpaRepository 사용하여 자동 저장.
        memberRepository.save(memberEntity);
        MemberEntity byUsername = memberRepository.findByUsername(memberDto.getUsername());
        System.out.println("메소드 잘 나오나 확인용"+byUsername);
    }
}
