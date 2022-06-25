package org.web.gamedev.rpg.model.forum;

import java.util.List;
import lombok.Data;

@Data
public class TagWithTopicsDto {

  private String name;

  private List<TopicDto> topics;
}
