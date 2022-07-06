package org.web.gamedev.rpg.auth.service;

import static org.web.gamedev.rpg.auth.config.JwtTokenUtil.REFRESH_TOKEN_EXPIRATION_IN_DAYS;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.web.gamedev.rpg.auth.config.JwtTokenUtil;
import org.web.gamedev.rpg.auth.exception.TokenRefreshException;
import org.web.gamedev.rpg.auth.model.RefreshToken;
import org.web.gamedev.rpg.auth.model.User;

@Service
@AllArgsConstructor
public class RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;
  private final JwtTokenUtil jwtTokenUtil;

  public Optional<RefreshToken> findById(Long id) {
    return refreshTokenRepository.findById(id);
  }

  public Optional<RefreshToken> findByUserId(Long userId) {
    return refreshTokenRepository.findByUserId(userId);
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  @Transactional
  public RefreshToken createRefreshToken(Long userId) {
    Instant issuedDate = LocalDateTime.now().toInstant(ZoneOffset.UTC);
    Instant expiredDate =
        LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRATION_IN_DAYS).toInstant(ZoneOffset.UTC);
    RefreshToken refreshTokenEntity = new RefreshToken();
    refreshTokenEntity.setUser(
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new RuntimeException(String.format("Could not find user with ID %d", userId))));
    refreshTokenRepository.deleteByUserId(userId);
    refreshTokenEntity.setIssuedDate(issuedDate);
    refreshTokenEntity.setExpirationDate(expiredDate);
    refreshTokenEntity.setToken(
        jwtTokenUtil.generateRefreshToken(
            Date.from(issuedDate), Date.from(expiredDate), userId.toString()));
    refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);
    return refreshTokenEntity;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpirationDate().isBefore(LocalDateTime.now().toInstant(ZoneOffset.UTC))) {
      throw new TokenRefreshException(
          token.getToken(), TokenRefreshException.MESSAGE_REFRESH_TOKEN_EXPIRED);
    }
    return token;
  }

  @Transactional
  public void deleteByUserId(Long userId) {
    Optional<User> userEntity = userRepository.findById(userId);
    if (userEntity.isPresent()) {
      refreshTokenRepository.deleteByUserId(userEntity.get().getId());
      refreshTokenRepository.flush();
    }
    throw new IllegalArgumentException(
        String.format("There are no users with id: [%d] in DB.", userId));
  }
}
