package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/{userId}/validate")
  public boolean validateUser(@PathVariable Long userId) {
    log.info("==== 进入用户验证端点 ==== userId: {}", userId);

    boolean result = userRepository.findById(userId).map(User::isActive).orElse(false);

    log.info("==== 验证结果 ==== userId: {}, active: {}", userId, result);
    return result;
  }
}
