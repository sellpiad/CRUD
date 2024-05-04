package com.example.demo.customFilter;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.example.demo.service.memberService.JwtTokenProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException, java.io.IOException {

        // HttpServletResponse로 변환
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        // Request Header에서 JWT 토큰 추출
        String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);

        // url 추출
        String url = req.getRequestURL().toString().substring(22);

        log.info("");
        log.info("At jwt filter: {}", accessToken);
        log.info("request url - {}", req.getRequestURL().toString().substring(22));
       

        if (accessToken!=null && !url.equals("refresh")) {
            // 토큰 검사 후, Authentication 객체를 가지고 와서 SecurityContext에 저장
            try {

                jwtTokenProvider.validateToken(accessToken);
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Success authenticated!");

            } catch (SecurityException | MalformedJwtException e) {
                log.info("Invalid JWT Token");
                res.sendError(403);
            } catch (ExpiredJwtException e) {
                log.info("Expired JWT Token");
                res.sendError(401);
            } catch (UnsupportedJwtException e) {
                log.info("Unsupported JWT Token");
                res.sendError(403);
            } catch (IllegalArgumentException e) {
                log.info("JWT claims string is empty.");
                res.sendError(403);
            }
        }

        chain.doFilter(request, res);

    }

}