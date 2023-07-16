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
        System.out.println("userRequest -> "+ super.loadUser(userRequest).getAttributes());

        return super.loadUser(userRequest);
    }
}
