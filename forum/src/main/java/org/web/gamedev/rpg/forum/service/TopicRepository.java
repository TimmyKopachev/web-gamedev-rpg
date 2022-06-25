package org.web.gamedev.rpg.forum.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.Topic;

@Repository
public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {

}
