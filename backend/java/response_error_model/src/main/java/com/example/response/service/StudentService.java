package com.example.response.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.response.entity.Student;
import com.example.response.repository.StudentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;

  public Student addStudent(String name, int grade) {
    Student student = new Student(name, grade);
    studentRepository.add(student);
    return student;
  }

  public List<Student> getOrderedStudents() {
    return studentRepository.getSorted();
  }

  public List<Student> getGradeStudents(int grade) {
    return studentRepository.getBy(grade);
  }
}
