package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank; // Jakarta EE 9+ 路径
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author fjgao
 * @date 2025/07/03
 */
@Data
public class NoteRequest {
  @NotNull(message = "用户ID不能为空")
  private Long userId;

  @NotBlank(message = "标题不能为空")
  @Size(max = 100, message = "标题长度不能超过100字符")
  private String title;

  @NotBlank(message = "内容不能为空")
  private String content;

  public NoteRequest() {}

  /** test */
  public NoteRequest(Long userId, String title, String content) {
    this.userId = userId;
    this.title = title;
    this.content = content;
  }
}
