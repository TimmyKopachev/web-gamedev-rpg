package org.web.gamedev.rpg.model.forum;

import java.time.Instant;
import java.util.Set;
import lombok.Data;

@Data
public class TopicDto {

  private String name;

  private Instant createdAt;

  private Instant lastModifiedAt;

  private String author;

  private Set<CommentDto> comments;
}
