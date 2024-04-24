package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.QBoard;
import com.example.demo.model.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  private static final QComment comment = QComment.comment;
  public Comment find(Long commentNo, Long boardNo) {
    return queryFactory.select(comment)
      .from(comment)
      .where(comment.commentNo.eq(commentNo))
      .where(QBoard.board.boardNo.eq(boardNo))
      .fetchOne();
  }
}
