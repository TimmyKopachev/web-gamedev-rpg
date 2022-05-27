package org.web.gamedev.rpg.forum.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.Comment;

@Repository
public interface MessageRepository extends JpaRepository<Comment, Long> {

  // TODO
}
