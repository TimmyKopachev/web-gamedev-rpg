package org.web.gamedev.rpg.model.forum;

import java.time.Instant;
import java.util.Set;
import lombok.Data;

@Data
public class CommentDto {

  private String content;
  private Instant createdAt;
  private transient String author;
  private Set<CommentDto> comments;
}
