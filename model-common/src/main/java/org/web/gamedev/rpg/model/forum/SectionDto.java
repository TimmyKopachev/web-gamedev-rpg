package org.web.gamedev.rpg.model.forum;

import java.util.List;
import lombok.Data;

@Data
public class SectionDto {

  private String name;
  private String description;
  private List<TopicWithTagsDto> topics;
}
