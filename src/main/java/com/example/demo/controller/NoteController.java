package com.example.demo.controller;

import com.example.demo.dto.NoteRequest;
import com.example.demo.dto.NoteResponse;
import com.example.demo.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author fjgao
 * @date 2025/07/03
 */
@RestController
@RequestMapping("/notes") // 基础路径调整为/notes
@RequiredArgsConstructor
public class NoteController {

  private final NoteService noteService;

  /** 创建笔记 POST /notes */
  @PostMapping
  public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
    NoteResponse response = noteService.createNote(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /** 查询笔记 GET /notes/{id} */
  @GetMapping("/{id}")
  public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
    NoteResponse response = noteService.getNoteById(id);
    return ResponseEntity.ok(response);
  }
}
