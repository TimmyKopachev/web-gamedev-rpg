package org.web.gamedev.rpg.auth.service;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.web.gamedev.rpg.auth.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByUserId(Long id);

  Optional<RefreshToken> findByToken(String id);

  void deleteByUserId(Long userId);
}
