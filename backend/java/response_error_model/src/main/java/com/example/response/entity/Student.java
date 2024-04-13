package com.example.response.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student implements Comparable<Student> {

  private String name;
  private int grade;

  @Override
  public int compareTo(Student student) {
//    return this.grade - student.grade;
    return student.grade - this.grade;
  }
}
