package org.web.gamedev.rpg.forum.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Entity
@Table(name = "section")
@NamedEntityGraph(
    name = "graph.section.topics.tags",
    attributeNodes = @NamedAttributeNode(value = "topics", subgraph = "topic.tags"),
    subgraphs = {
      @NamedSubgraph(
          name = "topic.tags",
          attributeNodes = {@NamedAttributeNode("tags")})
    })
public class Section extends IdEntity {

  private String description;

  @Enumerated(EnumType.STRING)
  private SectionType sectionType;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "section_id")
  private List<Topic> topics;
}
