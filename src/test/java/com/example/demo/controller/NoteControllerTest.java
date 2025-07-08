package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.NoteRequest;
import com.example.demo.dto.NoteResponse;
import com.example.demo.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Calendar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private NoteService noteService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void createNote_success() throws Exception {
    // 准备请求数据
    NoteRequest request = new NoteRequest(1L, "Test Title", "Test Content");

    // 模拟服务层返回的数据
    NoteResponse response =
        NoteResponse.builder()
            .id(1L)
            .userId(1L)
            .title("Test Title")
            .content("Test Content")
            .createTime(Calendar.getInstance())
            .updateTime(Calendar.getInstance())
            .build();
    when(noteService.createNote(any(NoteRequest.class))).thenReturn(response);

    // 执行请求并验证
    mockMvc
        .perform(
            post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.title").value("Test Title"))
        .andExpect(jsonPath("$.content").value("Test Content"));

    // 验证服务层方法被调用
    verify(noteService, times(1)).createNote(any(NoteRequest.class));
  }

  @Test
  void createNote_validationFail() throws Exception {
    // 构造一个无效的请求体（缺少 title）
    NoteRequest request = new NoteRequest(1L, "", "Test Content");

    mockMvc
        .perform(
            post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

    // 验证服务层方法未被调用
    verify(noteService, never()).createNote(any(NoteRequest.class));
  }
}
