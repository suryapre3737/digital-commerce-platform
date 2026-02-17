package com.digitalcommerce.userservice.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    @Value(("${jwt.secret}"))
    private String secret;
    @Value(("${jwt.expiration}"))
    private long expiration;

    public String generateToken(String email){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)), SignatureAlgorithm.HS256)
                .compact();
    }//read more

    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }//read more

    private Boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }//read more

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }//read more

}
