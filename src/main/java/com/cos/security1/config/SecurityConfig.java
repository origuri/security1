package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       /*
       * CSRF(Cross-Site Request Forgery) 공격을 방지하기 위한 기능을 비활성화하는 설정
       * jwt를 사용하면 어느정도 취약점이 보완되기 때문
       * */
       http.csrf().disable();


       /*
       *  url : /user/~                     : 로그인을 해야 들어올 수 있음.
       *        /manager/~                  : 권한이 manager나 admin만 들어올 수 있음.
       *        /admin/~                    : 권한이 admin인 사람만 들어올 수 있음.
       *        .anyRequest().permitAll();  : 그 이외의 url은 모두가 사용가능하다.
       *        .and()                      : 다른 설정을 이어서 하겠다.
       *        .formLogin()                : 메서드는 로그인 페이지와 인증 처리를 구성
       *                                      일반적으로 .loginPage("/login")와 함께 사용하여 로그인 페이지의 경로를 설정
       *        .loginPage("/login");       : formLogin에서 걸리면 /login 페이지로 이동시킨다.
       * */
       http.authorizeRequests()
               .antMatchers("/user/**").authenticated()
               .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANGER')")
               .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
               .anyRequest().permitAll()
               .and()
               .formLogin()
               .loginPage("/login");

       return http.build();
   }
}
