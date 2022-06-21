package org.web.gamedev.rpg.forum.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web.gamedev.rpg.forum.model.Section;

@Service
@AllArgsConstructor
public class SectionService {

  final SectionRepository discussionRepository;

  @Transactional(readOnly = true)
  public Section findDiscussion(Long discussionId) {
    return discussionRepository.findById(discussionId).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<Section> findAll() {
    return discussionRepository.findAll();
  }
}
