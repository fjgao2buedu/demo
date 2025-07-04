package com.example.demo.service;

import com.example.demo.dto.NoteRequest;
import com.example.demo.dto.NoteResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fjgao
 * @date 2025/07/03
 */
public interface NoteService {

  @Transactional
  NoteResponse createNote(NoteRequest request);

  NoteResponse getNoteById(Long id);
}
