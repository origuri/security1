package com.cos.security1.auth;

import com.cos.security1.entity.MemberEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/*
*   시큐리티가 /login 주소 요청이 들어오면 낚아채서 로그인을 진행 시킨다.
*   로그인 진행이 완료가 되면 시큐리티 session을 만들어 넣어준다.
*   Security ContextHolder에 세션을 저장하는데 Authentication 객체만 저장할 수 있다.
*   Authentication 객체 안에 있는 userDetails 타입에 member의 정보가 있어야 함.
*   Security Session -> Authentication -> userDetails(PrincipalDetails)
*
* */

// userDetails를 상속받음으로써 PrincipalDetails는 userDetails 타입이 되엇음.
// Oauth2도 같이 상속 받음으로써 일반 로그인과 구글 로그인 등을 모두 principalDetails에 위임할 수 있음.
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private MemberEntity memberEntity;
    private Map<String, Object> attributes; // Oauth용

    // 일반 로그인용
    public PrincipalDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    // Oauth2 로그인용 생성자 오버로딩
    public PrincipalDetails(MemberEntity memberEntity, Map<String, Object> attributes) {
        this.memberEntity = memberEntity;
        this.attributes = attributes;
    }

    // 해당 유저의 권한을 리턴하는 메소드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(memberEntity);
        // arrayList는 collection의 자식.
        Collection<GrantedAuthority> collection = new ArrayList<>();
        // GrantedAuthority 타입을 넣어줘야 함으로 새로운 객체를 생성해서 String 타입의 role을 리턴한다.
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberEntity.getRole();
            }
        });
        return collection;
    }

    // 패스워드를 가져오는 메소드
    @Override
    public String getPassword() {
        return memberEntity.getPassword();
    }

    // user 아이디를 가져오는 메소드
    @Override
    public String getUsername() {
        return memberEntity.getUsername();
    }

    /*
     * 사용자 계정의 유효기간이 지났거나 만료되었다면,
     * 이 메서드는 false를 반환, true는 계정이 만료되지 않았음.
     *
    * */
    @Override
    public boolean isAccountNonExpired() {
        return true ;
    }
    /*
    * 계정이 잠겨있는지 여부를 확인 false는 잠김 true는 열림
    * */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /*
    * 비밀번호를 너무 오래사용하지 않았는지 확인
    * false는 오래 사용했다. true는 오래사용하지 않았다.
    * */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
    *   너의 계정이 활성화 되어있니?
    *   false : 비활성화, true 활성화
    * */
    @Override
    public boolean isEnabled() {
        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼계정이 되기로 하는 비즈니스 로직이 있을 때 사용.
        // 현재 시간 - 현재 시간 = 1년 이상이면 return false;
        return true;
    }

    /*
    * PrincipalOauth2MemberService의 loadUser의 메소드에서 사용하는
    * getAttribute 메소드이다.
    * */
    @Override
    public Map<String, Object> getAttributes() {

        return attributes;

    }

    @Override
    public String getName() {
        return null;
    }
}
