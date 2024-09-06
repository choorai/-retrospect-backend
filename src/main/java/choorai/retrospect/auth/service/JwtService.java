package choorai.retrospect.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15; // 15분
    private final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7일

    private final Key secretKey;


    public JwtService(@Value("${jwt.secret.key}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String userEmail) {
        return generateToken(userEmail, ACCESS_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(String userEmail) {
        return generateToken(userEmail, REFRESH_TOKEN_VALIDITY);
    }

    private String generateToken(String userEmail, long validity) {
        Date now = new Date();
        return Jwts.builder()
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .setSubject(userEmail)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + validity))
            .compact();
    }

    public String extractUserEmail(String accessToken) {
        final Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(accessToken)
            .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            final Claims claims = getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Date extractExpiration(String token) {
        final Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

}
