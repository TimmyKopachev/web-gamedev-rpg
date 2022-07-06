package org.web.gamedev.rpg.auth.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Override
  Optional<User> findById(Long id);

  @Override
  List<User> findAll();

  Optional<User> findByUsername(String username);

  @Query(
      value = "SELECT u FROM users u LEFT JOIN FETCH u.roles where u.username = :username",
      nativeQuery = true)
  Optional<User> findByUsernameWithRoles(@Param("username") String username);
}
