package com.example.demo.controller;

import com.example.demo.dto.NoteRequest;
import com.example.demo.dto.NoteResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
  private final JwtTokenProvider jwtTokenProvider;

  /** 创建笔记 POST /notes */
  @Operation(summary = "创建新笔记", description = "创建新笔记并返回创建结果")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "笔记创建成功",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = NoteResponse.class))),
    @ApiResponse(responseCode = "400", description = "请求参数无效", content = @Content()),
  })
  @PostMapping
  public ResponseEntity<NoteResponse> createNote(
      @Valid @RequestBody NoteRequest request, Authentication authentication) {

    // 从认证信息中获取用户ID
    System.out.println(authentication.getPrincipal());

    User user = (User) authentication.getPrincipal();
    System.out.println(user.getId());
    Long userId = user.getId();

    NoteResponse response = noteService.createNote(request, userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /** 查询笔记 GET /notes/{id} */
  @Operation(summary = "根据笔记id查询笔记", description = "根据笔记id查询笔记并返回查询结果")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "查询成功",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = NoteResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "资源不存在",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ResourceNotFoundException.class))),
  })
  @GetMapping("/{id}")
  public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
    NoteResponse response = noteService.getNoteById(id);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteNote(@PathVariable Long id) {
    noteService.deleteNote(id);
  }

  @PostMapping("/{noteId}/tags")
  public ResponseEntity<NoteResponse> assignTags(
      @PathVariable Long noteId, @RequestBody Set<Long> tagIds) {
    return ResponseEntity.ok(noteService.assignTagsToNote(noteId, tagIds));
  }

  @DeleteMapping("/{noteId}/tags")
  public ResponseEntity<NoteResponse> removeTags(
      @PathVariable Long noteId, @RequestBody Set<Long> tagIds) {
    return ResponseEntity.ok(noteService.removeTagsFromNote(noteId, tagIds));
  }

  @PostMapping("/{noteId}/tags/by-names")
  public ResponseEntity<NoteResponse> assignTagsByName(
      @PathVariable Long noteId, @RequestBody Set<String> tagNames) {
    return ResponseEntity.ok(noteService.assignTagsToNoteByName(noteId, tagNames));
  }

  @DeleteMapping("/{noteId}/tags/by-names")
  public ResponseEntity<NoteResponse> removeTagsByName(
      @PathVariable Long noteId, @RequestBody Set<String> tagNames) {
    return ResponseEntity.ok(noteService.removeTagsFromNoteByName(noteId, tagNames));
  }
}
