package com.example.demo.service.memberService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo.service.dto.authorDto.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

    private final SecretKey key;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final String STRING_KEY_PREFIX = "auth:jwt:id:";

    // 키 설정
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT Token 발행
    public JwtToken generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();

        // Access Token
        Date accessTokenExpire = new Date(now + 1000 * 1);
        String accessToken = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .expiration(accessTokenExpire)
                .signWith(key)
                .compact();

        // Refresh Token
        String refreshToken = Jwts.builder()
                .expiration(new Date(now + 1000 * 60 * 60 * 24))
                .signWith(key)
                .compact();

        redisTemplate.opsForValue().set(STRING_KEY_PREFIX + authentication.getName(), refreshToken);

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public JwtToken regenerateAccessToken(String token) {

        Claims claims = parseClaims(token);

        String username = claims.getSubject();
        String authorities = (String) claims.get("auth");
        long now = new Date().getTime();
        
        if (redisTemplate.opsForValue().get(STRING_KEY_PREFIX + username) != null) {
            // Access Token
            Date accessTokenExpire = new Date(now + 1000 * 60);

            String accessToken = Jwts.builder()
                    .subject(username)
                    .claim("auth", authorities)
                    .expiration(accessTokenExpire)
                    .signWith(key)
                    .compact();

            return JwtToken.builder()
                    .grantType("Bearer")
                    .accessToken(accessToken)
                    .build();
        } else{

            return null;
        }

    }

    public boolean deleteToken(String accessToken){

        Claims claims = parseClaims(accessToken);

        String username = claims.getSubject();

        redisTemplate.delete(STRING_KEY_PREFIX + username);

        return true;
    }

    // Request Header에서 accessToken 정보 추출
    public String resolveAccessToken(HttpServletRequest request) throws StringIndexOutOfBoundsException {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // JWT Token 복호화 후 토큰에 들어있는 정보 꺼내기
    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null)
            throw new RuntimeException("There is no authentication");

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    /*
     * public boolean validateToken(String token) {
     * try {
     * Jwts.parser()
     * .verifyWith(key)
     * .build()
     * .parseSignedClaims(token);
     * 
     * return true;
     * } catch (SecurityException | MalformedJwtException e) {
     * log.info("Invalid JWT Token", e);
     * } catch (ExpiredJwtException e) {
     * log.info("Expired JWT Token", e);
     * } catch (UnsupportedJwtException e) {
     * log.info("Unsupported JWT Token", e);
     * } catch (IllegalArgumentException e) {
     * log.info("JWT claims string is empty.", e);
     * }
     * return false;
     * }
     */

    public boolean validateToken(String token) {

        Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

        return true;

    }

    // accessToken
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(this.key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
