package com.cos.security1.oauth;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.entity.MemberEntity;
import com.cos.security1.oauth.provider.GoogleMemberInfo;
import com.cos.security1.oauth.provider.OAuth2MemberInfo;
import com.cos.security1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2MemberService extends DefaultOAuth2UserService {

    // 비밀번호 암호화 용
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 구글로그인으로 회원가입이 되어있을 수 있으니 확인해야 함.
    private final MemberRepository memberRepository;

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

        OAuth2User oauth2User = super.loadUser(userRequest);
        // 구글 로그인 버튼 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴(OAuth client 라이브러리가 받아줌)
        // -> AccessToken을 요청해서 받는 것이 userRequest의 정보가 들어있음.
        // => userRequest 정보로 loadUser 함수를 호출하고 구글로 부터 회원프로필을 받음
        System.out.println("getAttribute -> "+ super.loadUser(userRequest).getAttributes());
        System.out.println("getAttribute -> "+ oauth2User.getAttributes());// 위에랑 같은 의미

        // 아래 정보로 구글 간편로그인으로 강제 회원가입 진행
        OAuth2MemberInfo oAuth2MemberInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2MemberInfo = new GoogleMemberInfo(oauth2User.getAttributes());
        }

        // google
        String provider = oAuth2MemberInfo.getProvider();
        // sub의 숫자값.
        String providerId = oAuth2MemberInfo.getProviderId();
        // 이메일
        String email = oAuth2MemberInfo.getEmail();
        // username이 겹치지 않게 uid를 만들어준다.
        // google_123412341234
        String username = provider+"_"+providerId;
        // 구글로 간편로그인을 하면 비밀번호가 필요없지만 그냥 넣어줌. 의미없음
        String password = bCryptPasswordEncoder.encode("겟인데어");
        // 권한.
        String role = "ROLE_USER";
        // 이미 구글로 회원가입 했는지 확인한다.
        MemberEntity memberEntity = memberRepository.findByUsername(username);
        if(memberEntity == null) {
            System.out.println("구글로그인이 최초입니다.");
            memberEntity = MemberEntity.toJoinGoogleEntity(username, password, email, role, provider, providerId);
            memberRepository.save(memberEntity);
        } else{
            System.out.println("이미 구글로 회원가입 했습니다. ");
        }
        // 두개의 객체가 authentication 객체 안으로 들어간다. 세션 안으로 들어감.
        // principalDetails에서 만든 구글로그인용 생성자.
        return new PrincipalDetails(memberEntity, oauth2User.getAttributes());
    }
}
