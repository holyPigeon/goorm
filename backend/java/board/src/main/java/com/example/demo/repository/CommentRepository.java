package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Modifying
  @Query(value = "UPDATE comment SET comment_status='DELETE' WHERE comment_no IN (:commentNos)", nativeQuery = true)
  void deleteCommentList(@Param("commentNos") Collection<Long> commentNos);
}
