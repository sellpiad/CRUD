package com.example.demo.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.service.dto.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;



@Component
@Slf4j
public class JwtTokenProvider {
    private final SecretKey key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key  = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(Authentication authentication){
        String authorities  = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

        long now = new Date().getTime();

        //Access Token
        Date accessTokenExpire =  new Date(now + 86400000);
        String accessToken = Jwts.builder()
        .subject(authentication.getName())
        .claim("auth", authorities)
        .expiration(accessTokenExpire)
        .signWith(key)
        .compact();

        //Refresh Token
        String refreshToekn = Jwts.builder()
        .expiration(new Date(now+86400000))
        .signWith(key)
        .compact();

        return JwtToken.builder()
        .grantType("Bearer")
        .accessToken(accessToken)
        .refreshToken(refreshToekn)
        .build();
    }

    // JWT Token 복호화 후 토큰에 들어있는 정보 꺼내기
    public Authentication getAuthentication(String accessToken){

        Claims claims = parseClaims(accessToken);

        if(claims.get("auth") == null)
            throw new RuntimeException("There no authentication");

        Collection<? extends GrantedAuthority> authorities =  Arrays.stream(claims.get("auth").toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(),"",authorities);
        
        return new UsernamePasswordAuthenticationToken(principal, "",authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }




    // accessToken
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
            .verifyWith(this.key)
            .build()
            .parseSignedClaims(accessToken)
            .getPayload();
            
                  
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
