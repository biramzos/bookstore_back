package com.example.bookstore_back.config;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class TokenService {
    private final String SECRETKEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateToken(String username){
        return Jwts
                .builder()
                .claim("username", username)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, SECRETKEY)
                .compact();
    }

    public String parseJwt(String token){
        return Jwts
                .parser()
                .setSigningKey(SECRETKEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validation(String token){
        try{
            Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}
