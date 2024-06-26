package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.customFilter.JwtAuthenticationFilter;
import com.example.demo.service.memberService.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@Slf4j
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.disable())
                .formLogin((formLogin) -> formLogin.successForwardUrl("/signin").failureForwardUrl("/failed"))
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests.anyRequest().permitAll())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
                                                                                                                                                                                                                                                            
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() throws Exception {
        return new BCryptPasswordEncoder();
    }

}
