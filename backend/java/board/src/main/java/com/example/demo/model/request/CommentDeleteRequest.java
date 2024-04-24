package com.example.demo.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDeleteRequest {
  private Long boardNo;
  private Long commentNo;
}
