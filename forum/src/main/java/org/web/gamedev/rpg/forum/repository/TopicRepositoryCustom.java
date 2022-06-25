package org.web.gamedev.rpg.forum.repository;

import java.util.List;
import org.web.gamedev.rpg.forum.model.Topic;
import org.web.gamedev.rpg.model.filter.TopicFilter;

interface TopicRepositoryCustom {

    List<Topic> find(TopicFilter filter);

}
