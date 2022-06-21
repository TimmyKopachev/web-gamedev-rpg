package org.web.gamedev.rpg.forum.mapper;

import org.mapstruct.Mapper;
import org.web.gamedev.rpg.forum.model.Tag;
import org.web.gamedev.rpg.model.forum.TagDto;
import org.web.gamedev.rpg.model.forum.TagWithTopicsDto;

@Mapper(
    componentModel = "spring",
    uses = {TopicMapper.class})
public abstract class TagMapper {

  public abstract TagDto mapToDto(Tag tag);

  public abstract TagWithTopicsDto mapToDtoFetch(Tag tag);

  public abstract Tag mapToEntity(TagDto tagDto);
}
