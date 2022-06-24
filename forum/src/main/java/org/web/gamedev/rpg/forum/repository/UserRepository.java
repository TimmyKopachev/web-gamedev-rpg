package org.web.gamedev.rpg.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Override
    Optional<UserEntity> findById(Long id);

    @Override
    List<UserEntity> findAll();

    Optional<UserEntity> findByUsername(String username);

    @Query("select u from UserEntity u left join fetch u.roles where u.username = ?1")
    Optional<UserEntity> findByUsernameWithRoles(String username);

}



