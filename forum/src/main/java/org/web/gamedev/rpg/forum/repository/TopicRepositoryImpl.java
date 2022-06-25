package org.web.gamedev.rpg.forum.service;

import static org.web.gamedev.rpg.forum.service.QuerydslFilterBuilder.AttributeFilter;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Set;
import org.web.gamedev.rpg.forum.model.QTopic;
import org.web.gamedev.rpg.forum.model.Topic;
import org.web.gamedev.rpg.model.filter.TopicFilter;

public class TopicRepositoryImpl extends AbstractRepositoryCustom<Topic, TopicFilter>
    implements TopicRepositoryCustom {

  @Override
  public List<Topic> find(TopicFilter filter) {
    QTopic topic = QTopic.topic;

    BooleanExpression filterExpression =
        new QuerydslFilterBuilder()
            .add(
                new AttributeFilter(
                    topicName -> topic.name.likeIgnoreCase("%" + topicName + "%"),
                    filter.getNamePart() == null ? null : Set.of(filter.getNamePart())))
            .build();

    JPAQuery<Topic> selectExpression = jpaQueryFactory.selectFrom(topic);

    return selectExpression.where(filterExpression).fetch();
  }
}
