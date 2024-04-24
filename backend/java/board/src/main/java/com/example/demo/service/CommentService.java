package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entity.Comment;
import com.example.demo.model.response.BoardResponse;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.BoardRepositoryCustom;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.CommentRepositoryCustom;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {


  private final BoardRepository boardRepository;

  private final BoardRepositoryCustom boardRepositoryCustom;

  private final CommentRepositoryCustom commentRepositoryCustom;

  private final CommentRepository commentRepository;


  public BoardResponse postComment(Long boardId, String commentBody) {
    return boardRepositoryCustom.find(boardId)
      .map(board -> board.addComment(commentBody))
      .map(boardRepository::save)
      .map(BoardResponse::from)
      .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
  }

  public void editComment(Long boardNo, Long commentNo, String commentBody) {
    Comment comment = commentRepositoryCustom.find(commentNo, boardNo);
    if(comment == null) throw new RuntimeException("존재하지 않는 댓글입니다.");
    comment.setBody(commentBody);
  }

  public void deleteComment(Long boardNo, Long commentNo) {
    Comment comment = commentRepositoryCustom.find(commentNo, boardNo);
    if(comment == null) throw new RuntimeException("존재하지 않는 댓글입니다.");
    commentRepository.delete(comment);
  }
}
