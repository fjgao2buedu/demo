package com.example.demo.exception;

public class DuplicateEntityException extends RuntimeException {
  public DuplicateEntityException(String message) {
    super(message);
  }
}
