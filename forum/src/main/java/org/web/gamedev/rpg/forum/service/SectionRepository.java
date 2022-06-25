package org.web.gamedev.rpg.forum.service;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

  @Override
  @EntityGraph(value = "graph.section.topics.tags", type = EntityGraph.EntityGraphType.LOAD)
  Optional<Section> findById(Long sectionID);
}
