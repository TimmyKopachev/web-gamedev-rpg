package org.web.gamedev.rpg.forum.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

  // TODO
}
