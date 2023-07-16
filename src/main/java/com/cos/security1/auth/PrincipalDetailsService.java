package com.cos.security1.auth;

import com.cos.security1.entity.MemberEntity;
import com.cos.security1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/*
* 시큐리티 설정에서 .loginProcessingUrl("/login") 해놨기 때문에
* /login 요청이 오면 자동으로 UserDetailService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행됨.
*
* */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    /*
    *  여기서 리턴한 userDetail 타입의 new PrincipalDetails(memberEntity); 는
    *  Authentication 객체안으로 들어간다.
    *  그럼 security session 안으로 들어갈 수 있다.
    * @Override
	    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
		return this.userDetailsService.loadUserByUsername(authentication.getName());
	}
    * */
    @Override           // 아이디의 input 태그의 name이 username이여야 매핑이되서 실행이 된다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("여기까지오나? -> "+username);
        MemberEntity memberEntity = memberRepository.findByUsername(username);
        System.out.println("memberEntitiy-> "+memberEntity);
        if(memberEntity != null){
            return new PrincipalDetails(memberEntity);
        } else {

            return null;
        }
    }
}
