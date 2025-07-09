package com.example.demo.service.impl;

import com.example.demo.dto.NoteRequest;
import com.example.demo.dto.NoteResponse;
import com.example.demo.entity.Note;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.NoteRepository;
import com.example.demo.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fjgao
 * @date 2025/07/03
 */
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
  private final NoteRepository noteRepository;

  @Transactional
  @Override
  public NoteResponse createNote(NoteRequest request) {
    Note note = new Note();
    note.setUserId(request.getUserId());
    note.setTitle(request.getTitle());
    note.setContent(request.getContent());

    Note saved = noteRepository.save(note);
    return convertToResponse(saved);
  }

  @Override
  public NoteResponse getNoteById(Long id) {
    // 1. 查询数据
    Note note =
        noteRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

    // 2. 实体转DTO
    return convertToResponse(note);
  }

  // 服务层
  @Override
  @Transactional
  public void deleteNote(Long id) {
    Note note =
        noteRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
    noteRepository.delete(note);
  }

  private NoteResponse convertToResponse(Note note) {
    return NoteResponse.builder()
        .id(note.getId())
        .userId(note.getUserId())
        .title(note.getTitle())
        .content(note.getContent())
        .createTime(note.getCreateTime())
        .updateTime(note.getUpdateTime())
        .build();
  }
}
