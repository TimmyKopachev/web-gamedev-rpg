package org.web.gamedev.rpg.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web.gamedev.rpg.auth.model.payload.CustomUserDetails;

@Service
public class JwtTokenUtil {

  private final String secret;

  public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
    this.secret = secret;
  }

  public static final ZoneId zoneId = ZoneId.systemDefault();
  public static final String SUBJECT_AUTH_REFRESH_TOKEN = "refresh_token";
  public static final Long REFRESH_TOKEN_EXPIRATION_IN_DAYS = 1L;
  public static final Long TOKEN_EXPIRATION_IN_MINUTES = 1L;

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
    return isTokenExpired(token);
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

  public String generateRefreshToken(Date issuedDate, Date expiredDate, String userId) {
    return Jwts.builder()
        .setSubject(SUBJECT_AUTH_REFRESH_TOKEN)
        .setAudience(userId)
        .setIssuedAt(issuedDate)
        .setExpiration(expiredDate)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  private String doGenerateToken(Map<String, Object> claims, String subject, String userId) {
    Date issuedDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    Date expiredDate =
        Date.from(
            LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_IN_MINUTES).toInstant(ZoneOffset.UTC));
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
    return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
  }

  private boolean isTokenExpired(String token) {
    Date date = getExpirationDateFromToken(token);
    Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    return date == null || !date.before(now);
  }
}
