package com.example.demo.event;

public record NoteCreatedEvent(
    Long noteId, Long userId, String title, String content, Long createdAt) {}
