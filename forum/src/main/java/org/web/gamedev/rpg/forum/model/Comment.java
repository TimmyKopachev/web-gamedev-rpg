package org.web.gamedev.rpg.forum.model;

import java.time.Instant;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

@ToString(exclude = {"parent", "comments"})
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"parent", "comments"})
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  private Comment parent;

  @OneToMany(
      fetch = FetchType.EAGER,
      mappedBy = "parent",
      cascade = CascadeType.REMOVE,
      orphanRemoval = true)
  private Set<Comment> comments;
}
