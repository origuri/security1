package com.cos.security1.contoller;

import com.cos.security1.dto.MemberDto;
import com.cos.security1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final MemberService memberSerivce;

    @GetMapping({"","/"})
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
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
