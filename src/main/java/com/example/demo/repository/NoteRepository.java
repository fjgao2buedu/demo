package com.example.demo.repository;

import com.example.demo.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fjgao
 * @date 2025/07/03
 */
public interface NoteRepository extends JpaRepository<Note, Long> {}
