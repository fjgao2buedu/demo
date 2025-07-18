package com.example.demo.service.impl;

import com.example.demo.entity.Tag;
import com.example.demo.exception.DuplicateEntityException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TagRepository;
import com.example.demo.service.TagService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  public TagServiceImpl(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  @Override
  public List<Tag> getAllTags() {
    return tagRepository.findAll();
  }

  @Override
  public Tag getTagById(Long id) {
    return tagRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
  }

  @Override
  public Tag createTag(Tag tag) {
    if (tagRepository.existsByName(tag.getName())) {
      throw new DuplicateEntityException("Tag with name '" + tag.getName() + "' already exists");
    }
    return tagRepository.save(tag);
  }

  @Override
  public Tag updateTag(Long id, Tag tagDetails) {
    Tag tag = getTagById(id);

    // 检查新名称是否重复（排除自身）
    if (!tag.getName().equals(tagDetails.getName())
        && tagRepository.existsByName(tagDetails.getName())) {
      throw new DuplicateEntityException(
          "Tag with name '" + tagDetails.getName() + "' already exists");
    }

    tag.setName(tagDetails.getName());
    return tagRepository.save(tag);
  }

  @Override
  public void deleteTag(Long id) {
    Tag tag = getTagById(id);
    tagRepository.delete(tag);
  }
}
