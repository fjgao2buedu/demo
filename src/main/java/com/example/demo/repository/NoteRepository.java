package com.example.demo.repository;

import com.example.demo.entity.Note;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fjgao
 * @date 2025/07/03
 */
public interface NoteRepository extends JpaRepository<Note, Long> {
  List<Note> findByUserId(Long userId);
}
