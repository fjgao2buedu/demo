package com.example.demo.dto;

import com.example.demo.entity.Tag;
import java.util.Calendar;
import java.util.Set;
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
  private Set<Tag> tags;
}
