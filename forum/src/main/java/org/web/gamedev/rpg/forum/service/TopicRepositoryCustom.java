package org.web.gamedev.rpg.forum.service;

import org.web.gamedev.rpg.forum.model.Topic;
import org.web.gamedev.rpg.model.filter.TopicFilter;

import java.util.List;

interface TopicRepositoryCustom {

    List<Topic> find(TopicFilter filter);

}
