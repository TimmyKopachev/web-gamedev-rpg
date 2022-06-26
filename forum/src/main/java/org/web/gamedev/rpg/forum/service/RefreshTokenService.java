package org.web.gamedev.rpg.forum.service;

import static org.web.gamedev.rpg.forum.config.JwtTokenUtil.REFRESH_TOKEN_EXPIRATION_IN_DAYS;
import static org.web.gamedev.rpg.forum.config.JwtTokenUtil.SUBJECT_AUTH_REFRESH_TOKEN;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.web.gamedev.rpg.forum.config.JwtTokenUtil;
import org.web.gamedev.rpg.forum.error.TokenRefreshException;
import org.web.gamedev.rpg.forum.model.entity.RefreshTokenEntity;
import org.web.gamedev.rpg.forum.model.entity.UserEntity;
import org.web.gamedev.rpg.forum.repository.RefreshTokenRepository;
import org.web.gamedev.rpg.forum.repository.UserRepository;

@Service
@AllArgsConstructor
public class RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;
  private final JwtTokenUtil jwtTokenUtil;

  public Optional<RefreshTokenEntity> findById(Long id) {
    return refreshTokenRepository.findById(id);
  }

  public Optional<RefreshTokenEntity> findByUserId(Long userId) {
    return refreshTokenRepository.findByUserId(userId);
  }

  public Optional<RefreshTokenEntity> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  @Transactional
  public RefreshTokenEntity createRefreshToken(Long userId) {
    Instant issuedDate = LocalDateTime.now().toInstant(ZoneOffset.UTC);
    Instant expiredDate =
        LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRATION_IN_DAYS).toInstant(ZoneOffset.UTC);
    RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
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
            Date.from(issuedDate),
            Date.from(expiredDate),
            SUBJECT_AUTH_REFRESH_TOKEN,
            userId.toString()));
    refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);
    return refreshTokenEntity;
  }

  public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
    if (token.getExpirationDate().isBefore(LocalDateTime.now().toInstant(ZoneOffset.UTC))) {
      throw new TokenRefreshException(
          token.getToken(), TokenRefreshException.MESSAGE_REFRESH_TOKEN_EXPIRED);
    }
    return token;
  }

  @Transactional
  public void deleteByUserId(Long userId) {
    Optional<UserEntity> userEntity = userRepository.findById(userId);
    if (userEntity.isPresent()) {
      refreshTokenRepository.deleteByUserId(userEntity.get().getId());
      refreshTokenRepository.flush();
    }
    throw new IllegalArgumentException(
        String.format("There are no users with id: [%d] in DB.", userId));
  }
}
