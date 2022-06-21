package org.web.gamedev.rpg.forum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.web.gamedev.rpg.forum.model.Section;
import org.web.gamedev.rpg.model.forum.SectionDto;

@Mapper(
    componentModel = "spring",
    uses = {TopicMapper.class})
public abstract class SectionMapper {

  @Mapping(target = "topics", source = "topics", ignore = true)
  public abstract SectionDto mapToDto(Section section);

  public abstract SectionDto mapToDtoFetch(Section section);

  @Mapping(target = "topics", source = "topics", ignore = true)
  public abstract Section mapToEntity(SectionDto sectionDto);
}
