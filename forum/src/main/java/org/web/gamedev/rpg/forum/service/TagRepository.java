package org.web.gamedev.rpg.forum.service;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  @Override
  @EntityGraph(value = "graph.tag.topics", type = EntityGraph.EntityGraphType.LOAD)
  Optional<Tag> findById(Long tagID);
}
