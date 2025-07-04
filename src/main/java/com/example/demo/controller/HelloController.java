package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fjgao
 * @date 2025/07/03
 */
@RestController
public class HelloController {
  @GetMapping("/health")
  public String health() {
    return "OK";
  }
}
