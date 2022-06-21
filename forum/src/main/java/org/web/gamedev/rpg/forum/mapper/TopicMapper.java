package org.web.gamedev.rpg.forum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.web.gamedev.rpg.forum.model.Topic;
import org.web.gamedev.rpg.model.forum.TopicDto;
import org.web.gamedev.rpg.model.forum.TopicWithTagsDto;

@Mapper(
    componentModel = "spring",
    uses = {CommentMapper.class})
public abstract class TopicMapper {

  @Mapping(target = "comments", source = "comments", ignore = true)
  public abstract TopicDto mapToDto(Topic topic);

  public abstract TopicWithTagsDto mapToDtoFetch(Topic topic);

  @Mapping(target = "comments", source = "comments", ignore = true)
  public abstract Topic mapToEntity(TopicDto topicDto);
}
