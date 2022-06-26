package org.web.gamedev.rpg.forum.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.web.gamedev.rpg.forum.model.payload.CustomUserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtTokenUtil {

    private final String secret;

    public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public final static ZoneId zoneId = ZoneId.systemDefault();
    public final static String SUBJECT_AUTH_REFRESH_TOKEN = "refresh_token";
    public final static Long REFRESH_TOKEN_EXPIRATION_IN_DAYS = 1L;
    public final static Long TOKEN_EXPIRATION_IN_MINUTES = 1L;

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public Boolean validateToken(String token, CustomUserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return Objects.equals(username, userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String generateToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getRoleList());
        return doGenerateToken(claims, userDetails.getUsername(), userDetails.getId().toString());
    }

    public String generateRefreshToken(Date issuedDate, Date expiredDate, String subject, String userId) {

        return Jwts.builder()
                .setSubject(SUBJECT_AUTH_REFRESH_TOKEN)
                .setAudience(userId)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, String userId) {
        Date issuedDate = Date.from(LocalDateTime.now()
                .toInstant(ZoneOffset.UTC));
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_IN_MINUTES).toInstant(ZoneOffset.UTC));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setAudience(userId)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        Date date = getExpirationDateFromToken(token);
        Date now = Date.from(LocalDateTime.now()
                .toInstant(ZoneOffset.UTC));
        return date != null && date.before(now);
    }
}