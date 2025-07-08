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
 * 笔记控制器，提供笔记相关的 RESTful API 接口。
 *
 * <p>该控制器实现了笔记功能的核心操作，包括创建和查询笔记。 所有接口均以 {@code /notes} 为基础路径。
 *
 * <p>示例用法：
 *
 * <pre>
 * // 创建笔记
 * POST /notes
 *
 * // 查询笔记
 * GET /notes/{id}
 * </pre>
 *
 * @author fjgao
 * @since 2025/07/03
 * @version 1.0
 * @see NoteService
 * @see NoteRequest
 * @see NoteResponse
 */
@RestController
@RequestMapping("/notes")
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
