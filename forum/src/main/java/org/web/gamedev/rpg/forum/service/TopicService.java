package org.web.gamedev.rpg.forum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.web.gamedev.rpg.forum.repository.TopicRepository;

@Service
@AllArgsConstructor
public class TopicService {

  private final TopicRepository instrumentRepository;
}
