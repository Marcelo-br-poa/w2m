package com.w2m.application.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "1234567891011121314151617181920arcobr";
    private static final Long KEY_VALIDITY = 1_296_000L; //15 dias

    public static String creationToken(String name, String username){
        long expTime = KEY_VALIDITY * 1_000;
        Date expDate = new Date(System.currentTimeMillis() + expTime);
        Map<String, Object> mapCreationToken = new HashMap<>();
        mapCreationToken.put("name", name);
        return  Jwts
                .builder()
                .setSubject(username)
                .setExpiration(expDate)
                .addClaims(mapCreationToken)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthToken(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(username,null, Collections.emptyList());

        }catch (JwtException e){
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }
}
