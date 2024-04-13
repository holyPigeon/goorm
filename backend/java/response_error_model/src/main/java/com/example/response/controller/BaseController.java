package com.example.response.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.response.ApiResponse;
import com.example.response.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;

abstract public class BaseController {

  @ExceptionHandler(CustomException.class)
  public <T> ApiResponse<T> customExceptionHandler(HttpServletResponse response, CustomException customException) {
    response.setStatus(customException.getErrorCode().getHttpStatus().value());

    return new ApiResponse<T>(
      customException.getErrorCode().getCode(),
      customException.getMessage(),
      customException.getData()
    );
  }

  public <T> ApiResponse<T> makeApiResponse(List<T> results) {
    return new ApiResponse<>(results);
  }

  public <T> ApiResponse<T> makeApiResponse(T result) {
    return new ApiResponse<>(result);
  }
}
