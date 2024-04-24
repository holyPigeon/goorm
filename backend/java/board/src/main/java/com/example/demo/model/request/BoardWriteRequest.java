package com.example.demo.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardWriteRequest {
  private String title;
  private String body;
}
