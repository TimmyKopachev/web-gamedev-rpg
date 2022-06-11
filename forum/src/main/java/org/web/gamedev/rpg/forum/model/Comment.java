package org.web.gamedev.rpg.forum.model;

import java.time.Instant;
import java.util.Iterator;
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
import org.springframework.data.annotation.CreatedDate;

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
  private transient String author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  private Comment parent;

  @OneToMany(
      fetch = FetchType.EAGER,
      mappedBy = "parent",
      cascade = CascadeType.REMOVE,
      orphanRemoval = true)
  private Set<Comment> comments;

  public String toString() {
    StringBuilder buffer = new StringBuilder(50);
    return print(buffer, "", "");
  }

  private String print(StringBuilder buffer, String prefix, String childrenPrefix) {
    buffer.append(prefix);
    buffer.append(getId());
    buffer.append(" ");
    buffer.append(content);
    buffer.append(" ");
    buffer.append(createdAt);
    buffer.append('\n');
    for (Iterator<Comment> it = comments.iterator(); it.hasNext(); ) {
      Comment next = it.next();
      if (it.hasNext()) {
        next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
      } else {
        next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
      }
    }
    return buffer.toString();
  }
}
