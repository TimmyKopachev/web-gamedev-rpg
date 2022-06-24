package org.web.gamedev.rpg.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.Comment;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Comment, Long> {
// TODO
}
