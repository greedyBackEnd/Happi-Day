package com.happiday.Happi_Day.jwt;

import com.happiday.Happi_Day.domain.entity.user.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtils {

    private final Key signingKey;
    private final Key refreshKey;
    private final JwtParser jwtParser;
    public final Long ACCESS_TOKEN_EXPIRATION_TIME;
    public final Long REFRESH_TOKEN_EXPIRATION_TIME;

    public JwtTokenUtils(
            @Value("${jwt.access-secret}") String jwtSecret,
            @Value("${jwt.refresh-secret}") String refreshSecret,
            @Value("${jwt.access-expiration-time}") Long accessExpirationTime,
            @Value("${jwt.refresh-expiration-time}") Long refreshExpirationTime
    ) {
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
        this.jwtParser = Jwts.parserBuilder().setSigningKey(this.signingKey).build();
        this.ACCESS_TOKEN_EXPIRATION_TIME = accessExpirationTime;
        this.REFRESH_TOKEN_EXPIRATION_TIME = refreshExpirationTime;
    }

    public boolean validate(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.warn("invalid jwt: {}", e.getMessage());
            return false;
        }
    }


    public Claims parseClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String generateToken(CustomUserDetails userDetails, Long expirationTime, Key key) {
        Claims jwtClaims = Jwts.claims()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(expirationTime)));

        return Jwts.builder().setClaims(jwtClaims).signWith(key).compact();
    }

    public String createAccessToken(CustomUserDetails userDetails) {
        return generateToken(userDetails, ACCESS_TOKEN_EXPIRATION_TIME, signingKey);
    }

    public String createRefreshToken(CustomUserDetails userDetails) {
        return generateToken(userDetails, REFRESH_TOKEN_EXPIRATION_TIME, refreshKey);
    }

    public String getUsername(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
            return claims.getBody().getSubject();
        } catch (ExpiredJwtException e) {
            log.warn("Expired Token: {}", e.getMessage());
            Claims claims = e.getClaims();
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Error extracting email from token: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateAccessToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
//            log.warn("invalid jwt: {}", e.getMessage());
            return false;
        } catch (Exception e) {
//            log.warn("invalid jwt: {}", e.getMessage());
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.warn("invalid jwt: {}", e.getMessage());
            return false;
        }
    }
}
