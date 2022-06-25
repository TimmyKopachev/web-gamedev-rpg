package org.web.gamedev.rpg.forum.web;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web.gamedev.rpg.forum.mapper.TopicMapper;
import org.web.gamedev.rpg.forum.model.Topic;
import org.web.gamedev.rpg.forum.service.TopicRepository;
import org.web.gamedev.rpg.forum.service.TopicService;
import org.web.gamedev.rpg.model.forum.TopicWithTagsDto;

@Slf4j
@RestController
@RequestMapping("/topics")
@AllArgsConstructor
public class TopicRestController {

  final TopicService topicService;
  final TopicMapper topicMapper;

  final TopicRepository topicRepository;

  @GetMapping
  public List<TopicWithTagsDto> getTopics(@RequestParam int page, @RequestParam int size) {
    log.info("fetching data for topic: page[{}] count[{}]", page, size);
    Iterator<Topic> sourceIterator = topicRepository.findAll().iterator();
    return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(sourceIterator, Spliterator.ORDERED), true)
        .map(topicMapper::mapToDtoFetch)
        .collect(Collectors.toList());
  }
}
