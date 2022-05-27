package org.web.gamedev.rpg.forum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TopicService {

  private final TopicRepository instrumentRepository;
}
