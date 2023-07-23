package com.cos.security1.contoller;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.dto.MemberDto;
import com.cos.security1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final MemberService memberSerivce;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
        System.out.println("테스트 로그인================ ");
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        System.out.println("autentication -> "+principalDetails.getMemberEntity());
        System.out.println("userDetail ==>  "+ userDetails.getMemberEntity());
        return "세션 정보 확인하기 ";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth){
        System.out.println("테스트 로그인================ ");
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        System.out.println("autentication -> "+oAuth2User.getAttributes());
        System.out.println("autentication -> "+oAuth.getAttributes());

        return "Oauth 세션 정보 확인하기 ";
    }

    @GetMapping({"","/"})
    public String index(){
        return "index";
    }

    // Oauth 로그인, 일반 로그인 전부 PrincipalDetails로 받을 수 잇음.
    //
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principaDetails => "+principalDetails.getMemberEntity());
        return "user";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }
    /*
    *   /login 주소는 security가 낚아챔!
    *   - SecurityConfig 설정 후 괜찮아짐.
    * */
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }


    @GetMapping("/joinForm")
    public  String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(MemberDto memberDto){
        memberSerivce.join(memberDto);
        System.out.println(memberDto);
        return "redirect:/loginForm";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){
        return "회원가입 완료됨!!";
    }

    @Secured("ROLE_ADMIN") // admin 권한만 가능
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // admin과 manager만 가능
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "data";
    }

}
