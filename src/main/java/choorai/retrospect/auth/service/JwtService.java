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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15; // 15분
    private final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7일

    private final Key secretKey;

    public JwtService(@Value("${jwt.secret.key}") final String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(final String userEmail) {
        return generateToken(userEmail, ACCESS_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(final String userEmail) {
        return generateToken(userEmail, REFRESH_TOKEN_VALIDITY);
    }

    private String generateToken(final String userEmail, final long validity) {
        final Date now = new Date();
        return Jwts.builder()
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .setSubject(userEmail)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + validity))
            .compact();
    }

    public String extractUserEmail(final String accessToken) {
        final Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(accessToken)
            .getBody();
        return claims.getSubject();
    }

    public boolean isTokenValid(final String token, final UserDetails userDetails) {
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(final String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Date extractExpiration(final String token) {
        final Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    private Claims getClaims(final String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

}
