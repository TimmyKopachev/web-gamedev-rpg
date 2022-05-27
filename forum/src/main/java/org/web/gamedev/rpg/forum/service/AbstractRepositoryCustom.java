package org.web.gamedev.rpg.forum.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.web.gamedev.rpg.forum.model.IdEntity;
import org.web.gamedev.rpg.forum.model.dto.Filter;

public abstract class AbstractRepositoryCustom<E extends IdEntity, F extends Filter> {

  @Autowired protected EntityManager entityManager;

  protected JPAQueryFactory jpaQueryFactory;

  @PostConstruct
  void setUp() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  protected abstract List<E> find(F filter);
}
