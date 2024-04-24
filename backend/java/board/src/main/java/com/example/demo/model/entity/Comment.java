package com.example.demo.model.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Where(clause = "comment_status='ACTIVE'")
@SQLDelete(sql = "UPDATE comment SET comment_status='DELETE' where comment_no=?")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentNo;

  private String body;

  @Enumerated(EnumType.STRING)
  private CommentStatus commentStatus;


  @ManyToOne
  private Board board;
}
