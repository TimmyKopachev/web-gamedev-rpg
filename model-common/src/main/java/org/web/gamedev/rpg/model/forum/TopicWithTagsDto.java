package org.web.gamedev.rpg.model.forum;

import java.time.Instant;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TopicWithTagsDto {

  private String name;

  private Instant createdAt;

  private Instant lastModifiedAt;

  private String author;

  private Set<CommentDto> comments;

  private Set<TagDto> tags;
}
