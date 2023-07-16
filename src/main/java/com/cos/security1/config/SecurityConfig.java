package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{
    // 해당 메소드를 ioc에 등록해줌.
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }



    /*
    *
    * Spring Security는 여러 개의 필터로 구성된 필터 체인을 사용하여 보안 기능을 적용.
    * 각 필터는 특정한 보안 작업을 수행하고, 필터 체인을 따라 순서대로 처리됨..
    * SecurityFilterChain은 이러한 필터 체인을 정의하고 구성하는 역할을 담당합니다.
    * */
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       /*
       * CSRF(Cross-Site Request Forgery) 공격을 방지하기 위한 기능을 비활성화하는 설정
       * jwt를 사용하면 어느정도 취약점이 보완되기 때문
       * */
       http.csrf().disable();
       System.out.println("여기는 config");

       /*
       *  url : /user/~                     : 로그인을 해야 들어올 수 있음.
       *        /manager/~                  : 권한이 manager나 admin만 들어올 수 있음.
       *        /admin/~                    : 권한이 admin인 사람만 들어올 수 있음.
       *        .anyRequest().permitAll();  : 그 이외의 url은 모두가 사용가능하다.
       *        .and()                      : 다른 설정을 이어서 하겠다.
       *        .formLogin()                : 메서드는 로그인 페이지와 인증 처리를 구성
       *                                      일반적으로 .loginPage("/login")와 함께 사용하여 로그인 페이지의 경로를 설정
       *        .loginPage("/loginForm");   : formLogin에서 걸리면 /loginForm 페이지로 이동시킨다.
       *        .loginProcessingUrl("/login")   : /login 주소과 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행준다.
       *                                          로그인 로직(비밀번호 해시비교 등) 안해줘도 됨.
       *        .defaultSuccessUrl("/")     : 로그인 성공하면 메인 페이지로 이동시켜줌.
       * */
       http.authorizeRequests()
               .antMatchers("/user/**").authenticated()
               .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANGER')")
               .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
               .anyRequest().permitAll()
               .and()
               .formLogin()
               .loginPage("/loginForm")
               .loginProcessingUrl("/loginProc")
               .defaultSuccessUrl("/")
               .failureUrl("/");

       return http.build();
   }
}
