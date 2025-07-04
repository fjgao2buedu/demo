package com.example.demo.exception;

/**
 * @author fjgao
 * @date 2025/07/03
 */
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
