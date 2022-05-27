package org.web.gamedev.rpg.forum.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.Discussion;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

  // TODO
}
