package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.dto.NoteRequest;
import com.example.demo.dto.NoteResponse;
import com.example.demo.entity.Note;
import com.example.demo.repository.NoteRepository;
import com.example.demo.service.impl.NoteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author fjgao
 * @date 2025/07/04
 */
@ExtendWith(MockitoExtension.class) // 启用Mockito
class NoteServiceTest {

  @Mock private NoteRepository noteRepository; // 依赖的Repository

  @InjectMocks private NoteServiceImpl noteService; // 被测试的Service

  @Test
  void createNote_shouldReturnSavedNote() {
    // 1. 准备测试数据
    NoteRequest request = new NoteRequest(1L, "Test Title", "Content");
    Note savedNote = new Note(1L, 1L, "Test Title", "Content");

    // 2. 定义Mock行为
    when(noteRepository.save(any(Note.class))).thenReturn(savedNote);

    // 3. 调用被测方法
    NoteResponse response = noteService.createNote(request, 1L);

    // 4. 验证结果
    assertEquals(1L, response.getId());
    assertEquals(1L, response.getUserId());
    assertEquals("Test Title", response.getTitle());
    assertEquals("Content", response.getContent());

    // 5. 验证Mock交互
    verify(noteRepository, times(1)).save(any(Note.class));
  }
}
