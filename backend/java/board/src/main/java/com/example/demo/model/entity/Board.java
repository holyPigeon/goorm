package com.example.demo.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Where(clause = "board_status='ACTIVE'")
@SQLDelete(sql = "UPDATE board SET board_status='DELETE' WHERE board_no=?")
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long boardNo;

  private String title;

  private String body;

  @Enumerated(EnumType.STRING)
  private BoardStatus boardStatus;

  @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Comment> comments = new ArrayList<>();

  public Board addComment(String commentBody) {
    Comment comment = new Comment();
    comment.setBody(commentBody);
    comment.setBoard(this);
    comment.setCommentStatus(CommentStatus.ACTIVE);
    this.comments.add(comment);
    return this;
  }


}
