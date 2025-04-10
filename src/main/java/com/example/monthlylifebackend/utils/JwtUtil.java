package com.example.monthlylifebackend.utils;

import com.example.monthlylifebackend.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static String SECRET;
    private static Long EXPIRATION;

    @Value("${jwt.secret}")
    public void setSECRET(String value) {
        JwtUtil.SECRET = value;
    }
    @Value("${jwt.expiration}")
    public void setEXPIRATION(Long value) {
        JwtUtil.EXPIRATION = value;
    }


    public static String generateToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static User buildUserDataFromToken(String token) {
        try {
            Claims claim = Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
            return User.builder().id(claim.get("userId", String.class)).build();

        } catch (Exception e) {
            return null;
        }
    }
}
