package com.cos.security1.repository;

import com.cos.security1.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // findBy어쩌구 하면 select * from entity where 어쩌구 = ? 이런 쿼리가 발생함.
    public MemberEntity findByUsername(String username);
}
