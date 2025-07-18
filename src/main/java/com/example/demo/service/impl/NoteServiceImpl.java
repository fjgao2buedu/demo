package com.example.demo.service.impl;

import com.example.demo.client.UserClient;
import com.example.demo.dto.NoteRequest;
import com.example.demo.dto.NoteResponse;
import com.example.demo.entity.Note;
import com.example.demo.entity.Tag;
import com.example.demo.event.NoteCreatedEvent;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ServiceNotAvailableException;
import com.example.demo.exception.UserNotValidException;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.service.NoteService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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
  private final TagRepository tagRepository;
  private final UserClient userClient;
  @Autowired private KafkaTemplate<String, Object> kafkaTemplate;

  @Transactional
  @Override
  public NoteResponse createNote(NoteRequest request, Long userId) {
    //    // 通过Feign验证用户
    //    validateUser(userId);
    System.out.println("==== 开始创建笔记 ====");
    System.out.println("用户ID: " + userId);
    System.out.println("标题: " + request.getTitle());

    try {
      System.out.println("调用用户验证服务...");
      boolean isValid = userClient.validateUser(userId);
      System.out.println("用户验证结果: " + isValid);

      if (!isValid) {
        throw new UserNotValidException("用户验证失败");
      }

      // 创建笔记逻辑...
      Note note = new Note();
      note.setUserId(userId);
      note.setTitle(request.getTitle());
      note.setContent(request.getContent());

      Note saved = noteRepository.save(note);
      // 新增：发送事件
      kafkaTemplate.send(
          "note-created",
          new NoteCreatedEvent(
              note.getId(),
              userId,
              note.getTitle(),
              note.getContent(),
              note.getCreateTime().getTimeInMillis()));
      return convertToResponse(saved);
      //
    } catch (Exception e) {
      System.err.println("创建笔记时出错: " + e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }

  private void validateUser(Long userId) {
    try {
      if (!userClient.validateUser(userId)) {
        throw new UserNotValidException("用户验证失败");
      }
    } catch (Exception e) {
      throw new ServiceNotAvailableException("用户服务不可用");
    }
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
        .tags(note.getTags())
        .build();
  }

  @Override
  @Transactional
  public NoteResponse assignTagsToNote(Long noteId, Set<Long> tagIds) {
    Note note =
        noteRepository
            .findById(noteId)
            .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

    Set<Tag> tags = tagRepository.findByIdIn(tagIds);
    tags.forEach(note::addTag);

    Note saved = noteRepository.save(note);
    return convertToResponse(saved);
  }

  @Override
  @Transactional
  public NoteResponse removeTagsFromNote(Long noteId, Set<Long> tagIds) {
    Note note =
        noteRepository
            .findById(noteId)
            .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

    Set<Tag> tagsToRemove = tagRepository.findByIdIn(tagIds);
    tagsToRemove.forEach(note::removeTag);

    Note saved = noteRepository.save(note);
    return convertToResponse(saved);
  }

  @Override
  @Transactional
  public NoteResponse assignTagsToNoteByName(Long noteId, Set<String> tagNames) {
    Note note =
        noteRepository
            .findById(noteId)
            .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

    Set<Tag> existingTags = tagRepository.findByNameIn(tagNames);
    Set<String> existingTagNames =
        existingTags.stream().map(Tag::getName).collect(Collectors.toSet());

    // 创建不存在的标签
    Set<Tag> tagsToAdd = new HashSet<>(existingTags);
    for (String name : tagNames) {
      if (!existingTagNames.contains(name)) {
        Tag newTag = new Tag();
        newTag.setName(name);
        tagsToAdd.add(tagRepository.save(newTag));
      }
    }

    tagsToAdd.forEach(note::addTag);
    Note saved = noteRepository.save(note);
    return convertToResponse(saved);
  }

  @Override
  @Transactional
  public NoteResponse removeTagsFromNoteByName(Long noteId, Set<String> tagNames) {
    Note note =
        noteRepository
            .findById(noteId)
            .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

    Set<Tag> tagsToRemove = tagRepository.findByNameIn(tagNames);
    tagsToRemove.forEach(note::removeTag);

    Note saved = noteRepository.save(note);
    return convertToResponse(saved);
  }
}
