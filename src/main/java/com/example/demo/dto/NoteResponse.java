package com.example.demo.dto;

import java.util.Calendar;
import lombok.Builder;
import lombok.Data;

/**
 * @author fjgao
 * @date 2025/07/03
 */
@Data
@Builder
public class NoteResponse {
  private Long id;
  private Long userId;
  private String title;
  private String content;
  private Calendar createTime;
  private Calendar updateTime;
}
