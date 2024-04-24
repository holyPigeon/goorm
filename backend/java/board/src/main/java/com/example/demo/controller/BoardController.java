package com.example.demo.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.request.BoardDeleteRequest;
import com.example.demo.model.request.BoardEditRequest;
import com.example.demo.model.request.BoardWriteRequest;
import com.example.demo.model.response.BoardResponse;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping("boardList")
  public List<BoardResponse> searchBoardList(
    @RequestParam("page") int page,
    @RequestParam("pageSize") int pageSize,
    @RequestParam("direction") Sort.Direction direction
  ) {
    return boardService.searchBoardList(page, pageSize, direction);
  }


  @GetMapping("board")
  public BoardResponse searchBoard(
    @RequestParam("boardNo") Long boardNo
  ) {
    return boardService.getBoard(boardNo);
  }

  @PostMapping("board")
  public BoardResponse writeBoard(
    @RequestBody BoardWriteRequest boardWriteRequest
  ) {
    return boardService.writeBoard(boardWriteRequest.getTitle(), boardWriteRequest.getBody());
  }

  @PutMapping("board")
  public BoardResponse editBoard(
    @RequestBody BoardEditRequest boardEditRequest
  ) {
    return boardService.editBoard(boardEditRequest.getBoardNo(), boardEditRequest.getBody());
  }

  @DeleteMapping("board")
  public Long deleteBoard(
    @RequestBody BoardDeleteRequest boardDeleteRequest
  ) {
    return boardService.deleteBoard(boardDeleteRequest.getBoardNo());
  }
}
