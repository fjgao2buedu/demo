package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
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

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "note_tags",
      joinColumns = @JoinColumn(name = "note_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new HashSet<>();

  public Note() {}

  /** test */
  public Note(Long id, Long userId, String title, String content) {
    this.id = id;
    this.userId = userId;
    this.title = title;
    this.content = content;
  }

  // 添加和移除标签的辅助方法
  public void addTag(Tag tag) {
    this.tags.add(tag);
    tag.getNotes().add(this);
  }

  public void removeTag(Tag tag) {
    this.tags.remove(tag);
    tag.getNotes().remove(this);
  }
}
