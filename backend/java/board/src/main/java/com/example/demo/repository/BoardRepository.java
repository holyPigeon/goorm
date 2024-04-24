package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

  Page<Board> findAll(Pageable pageable);
}
