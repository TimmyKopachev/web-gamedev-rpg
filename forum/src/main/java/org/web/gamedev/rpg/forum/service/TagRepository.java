package org.web.gamedev.rpg.forum.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  // TODO
}
