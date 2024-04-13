package com.example.response.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.response.ApiResponse;
import com.example.response.entity.Student;
import com.example.response.exception.CustomException;
import com.example.response.exception.ErrorCode;
import com.example.response.exception.data.InputRestriction;
import com.example.response.service.StudentService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MyController extends BaseController{

  private final StudentService studentService;

  @GetMapping("/student/{name}/{grade}")
  public ApiResponse<Student> add(
    @PathVariable("name") String name,
    @PathVariable("grade") int grade
  ) {
    if(grade > 5) {
      throw new CustomException(ErrorCode.BAD_REQUEST, "grade 는 6이상 입력 할 수 없습니다.", new InputRestriction(5));
    }

    Student student = studentService.addStudent(name, grade);
    return makeApiResponse(student);
  }

  @GetMapping("/student/ordered")
  public ApiResponse<Student> getOrdered() {
    List<Student> students = studentService.getOrderedStudents();
    return makeApiResponse(students);
  }

  @GetMapping("/student/{grade}")
  public ApiResponse<Student> getSameGrade(
    @PathVariable("grade") int grade
  ) {
    List<Student> students = studentService.getGradeStudents(grade);
    return makeApiResponse(students);
  }
}
