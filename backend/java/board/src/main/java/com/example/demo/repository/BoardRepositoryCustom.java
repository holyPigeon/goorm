package com.example.demo.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Board;
import com.example.demo.model.entity.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  private final static QBoard board = QBoard.board;

  public Optional<Board> find(Long boardNo) {
    return Optional.ofNullable(
      queryFactory.select(board)
        .from(board)
        .leftJoin(board.comments).fetchJoin()
        .where(board.boardNo.eq(boardNo))
        .fetchOne()
    );
  }

}
