package org.web.gamedev.rpg.forum.service;

import java.util.List;
import org.web.gamedev.rpg.forum.model.Topic;
import org.web.gamedev.rpg.forum.model.dto.TopicFilter;

interface TopicRepositoryCustom {

    List<Topic> find(TopicFilter filter);

}
