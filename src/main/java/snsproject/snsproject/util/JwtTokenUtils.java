package snsproject.snsproject.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtTokenUtils {

    public static String generateToken(String userName, String secretKey, long expiredTimeMs) {
        // 키 생성
        Key signingKey = getKey(secretKey);

        // 토큰 생성
        return Jwts.builder()
                .setSubject(userName) // 'subject' 클레임으로 사용자 이름 설정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs)) // 만료 시간 설정
                .signWith(signingKey, SignatureAlgorithm.HS256) // 서명 알고리즘과 키 지정
                .compact(); // JWT 생성
    }

    private static Key getKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
