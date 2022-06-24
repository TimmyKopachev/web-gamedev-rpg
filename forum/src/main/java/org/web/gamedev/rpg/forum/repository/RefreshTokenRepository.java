package org.web.gamedev.rpg.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.gamedev.rpg.forum.model.entity.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByUserId(Long id);

    Optional<RefreshTokenEntity> findByToken(String id);

    void deleteByUserId(Long userId);
}