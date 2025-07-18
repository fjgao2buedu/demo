package com.example.demo.service;

import com.example.demo.entity.Tag;
import java.util.List;

public interface TagService {
  List<Tag> getAllTags();

  Tag getTagById(Long id);

  Tag createTag(Tag tag);

  Tag updateTag(Long id, Tag tagDetails);

  void deleteTag(Long id);
}
