package org.web.gamedev.rpg.forum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.web.gamedev.rpg.forum.model.Topic;

@Service
@AllArgsConstructor
public class TopicService {

  private final TopicRepository topicRepository;

  public Page<Topic> findAll(Pageable page) {
    return topicRepository.findAll(page);
  }
}
