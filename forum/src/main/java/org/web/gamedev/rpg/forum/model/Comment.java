package org.web.gamedev.rpg.forum.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Entity
@Table(name = "comment")
public class Comment extends IdEntity {

  private String content;

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  // @CreatedBy
  private String author;
}
