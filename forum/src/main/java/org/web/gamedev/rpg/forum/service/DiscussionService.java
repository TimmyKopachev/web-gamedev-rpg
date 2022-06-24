package org.web.gamedev.rpg.forum.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web.gamedev.rpg.forum.model.Discussion;
import org.web.gamedev.rpg.forum.repository.DiscussionRepository;

@Service
@AllArgsConstructor
public class DiscussionService {

  final DiscussionRepository discussionRepository;

  @Transactional(readOnly = true)
  public Discussion findDiscussion(Long discussionId) {
    return discussionRepository.findById(discussionId).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<Discussion> findAll() {
    return discussionRepository.findAll();
  }
}
