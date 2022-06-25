package org.web.gamedev.rpg.forum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.web.gamedev.rpg.forum.model.Comment;
import org.web.gamedev.rpg.model.forum.CommentDto;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CommentMapper {

  public abstract CommentDto mapToDto(Comment comment);

  public abstract Comment mapToEntity(CommentDto commentDto);
}
