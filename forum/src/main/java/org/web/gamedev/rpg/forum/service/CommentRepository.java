package org.web.gamedev.rpg.forum.service;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.Comment;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

  @Query(
      value =
          "WITH RECURSIVE comments_tree_by_topic AS ("
              + " SELECT id, content, author, created_at, parent_id "
              + " FROM comment "
              + " WHERE topic_id = :topicId "
              + "   UNION ALL"
              + " SELECT c.id, c.content, c.author, c.created_at, c.parent_id FROM comment c "
              + " INNER JOIN comments_tree_by_topic ctbt ON ctbt.id = c.parent_id "
              + ")"
              + " SELECT id, content, author, created_at, parent_id"
              + " FROM comments_tree_by_topic "
              + " WHERE parent_id IS NULL"
              + " ORDER BY created_at DESC",
      nativeQuery = true)
  Set<Comment> findAllByTopicId(@Param("topicId") Long topicId);
}
