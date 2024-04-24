package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.request.CommentDeleteRequest;
import com.example.demo.model.request.CommentEditRequest;
import com.example.demo.model.request.CommentPostRequest;
import com.example.demo.model.response.BoardResponse;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping("comment")
  public BoardResponse post(
    @RequestBody CommentPostRequest commentPostRequest
  ) {
    return commentService.postComment(commentPostRequest.getBoardNo(), commentPostRequest.getCommentBody());
  }

  @PutMapping("comment")
  public String edit(
    @RequestBody CommentEditRequest commentEditRequest
  ) {
    commentService.editComment(commentEditRequest.getBoardNo(), commentEditRequest.getCommentNo(), commentEditRequest.getCommentBody());

    return "OK";
  }

  @DeleteMapping("comment")
  public String delete(
    @RequestBody CommentDeleteRequest commentDeleteRequest
  ) {

    commentService.deleteComment(commentDeleteRequest.getBoardNo(), commentDeleteRequest.getCommentNo());

    return "OK";
  }
}
