package com.cos.security1.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2MemberService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser (OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // registarionId로 어떤 Oauth로 로그인 했는지 확인가능
        System.out.println("userRequest -> "+userRequest.getClientRegistration());
        System.out.println("userRequest -> "+userRequest.getAccessToken().getTokenValue());
        System.out.println("userRequest -> "+userRequest.getClientRegistration().getClientId());
        /*
            super.loadUser(userRequest).getAttributes()의 값
        *   {sub=구글의 pk값,
        *   name=내이름,
        *   given_name=이름,
        *   family_name=내,
        *   picture=내 프사,
        *   email=내 구글 이메일,
        *   email_verified=true,
        *   locale=ko}

            DB 저장 요소
            username = google_구글의 pk값
            password = 암호화해서 겟인데어 사실 null만 아니면 됨.
            email    = 내 구글 이메일
            role     = ROLE_USER

        * */
        // 구글 로그인 버튼 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴(OAuth client 라이브러리가 받아줌)
        // -> AccessToken을 요청해서 받는 것이 userRequest의 정보가 들어있음.
        // => userRequest 정보로 loadUser 함수를 호출하고 구글로 부터 회원프로필을 받음
        System.out.println("userRequest -> "+ super.loadUser(userRequest).getAttributes());

        OAuth2User oauth2User = super.loadUser(userRequest);

        return super.loadUser(userRequest);
    }
}
