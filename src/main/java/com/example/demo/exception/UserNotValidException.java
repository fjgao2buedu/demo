package com.example.demo.exception;

public class UserNotValidException extends RuntimeException {
  public UserNotValidException(String message) {
    super(message);
  }
}
