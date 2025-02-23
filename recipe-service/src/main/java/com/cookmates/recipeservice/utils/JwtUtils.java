package com.cookmates.recipeservice.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class JwtUtils {
    private final Logger log = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${jwt.secret}")
    private String SECRET;

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            return claims!=null && claims.getSubject()!=null;
        }catch (ExpiredJwtException e){
            log.error("Expired JWT token");
        }catch (UnsupportedJwtException e){
            log.error("Unsupported JWT token");
        }catch (MalformedJwtException e){
            log.error("Malformed JWT token");
        }catch (SignatureException e){
            log.error("Invalid JWT token");
        }catch (IllegalArgumentException e){
            log.error("JWT claims string is empty");
        }
        return false;
    }
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }


    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
