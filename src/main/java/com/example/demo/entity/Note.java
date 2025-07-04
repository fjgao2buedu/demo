package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author fjgao
 * @date 2025/07/03
 */
@Entity
@Table(name = "notes")
@Getter
@Setter
public class Note {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @CreationTimestamp
  @Column(updatable = false)
  private Calendar createTime;

  @UpdateTimestamp private Calendar updateTime;
}
