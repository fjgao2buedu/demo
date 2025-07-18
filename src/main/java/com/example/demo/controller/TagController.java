package com.example.demo.controller;

import com.example.demo.entity.Tag;
import com.example.demo.service.TagService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tags")
public class TagController {

  private final TagService tagService;

  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @GetMapping
  public ResponseEntity<List<Tag>> getAllTags() {
    return ResponseEntity.ok(tagService.getAllTags());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
    return ResponseEntity.ok(tagService.getTagById(id));
  }

  @PostMapping
  public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
    return new ResponseEntity<>(tagService.createTag(tag), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag tagDetails) {
    return ResponseEntity.ok(tagService.updateTag(id, tagDetails));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
    tagService.deleteTag(id);
    return ResponseEntity.noContent().build();
  }
}
