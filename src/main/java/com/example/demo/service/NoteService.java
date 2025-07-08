package com.example.demo.service;

import com.example.demo.dto.NoteRequest;
import com.example.demo.dto.NoteResponse;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fjgao
 * @date 2025/07/03
 */
public interface NoteService {

  /**
   * 创建新的笔记。
   *
   * <p>此方法在事务中执行，确保数据操作的原子性和一致性。当操作失败时，所有数据库修改将会回滚。
   *
   * @param request 包含新笔记内容的请求对象，不可为 {@code null}
   * @return 包含已创建笔记详细信息的响应对象，不会返回 {@code null}
   */
  @Transactional
  NoteResponse createNote(NoteRequest request);

  /**
   * 根据笔记唯一标识获取笔记详细信息。
   *
   * @param id 要查询的笔记ID，必须为有效正整数
   * @return 包含笔记完整数据的响应对象
   * @throws ResourceNotFoundException 如果指定ID的笔记不存在
   */
  NoteResponse getNoteById(Long id);
}
