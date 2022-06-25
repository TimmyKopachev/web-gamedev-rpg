package org.web.gamedev.rpg.forum.service;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.model.Comment;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

  @Query(
      value = """  
       WITH RECURSIVE comments_tree (id, content, created_at, parent_id) AS (
       SELECT id, content, created_at, parent_id
       FROM comment
       WHERE topic_id = 1
         UNION
       SELECT c.id, c.content, c.created_at, c.parent_id FROM comment c
       INNER JOIN comments_tree ct ON ct.id = c.parent_id  )
       SELECT id, content, created_at, parent_id
      FROM comments_tree
      WHERE parent_id IS NULL
      ORDER BY created_at DESC
      """,
      nativeQuery = true)
  Set<Comment> findCommentsByTopicId();
  //Set<Comment> findCommentsByTopicId(@Param("topicId") Long topicId);

}
