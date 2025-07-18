package com.example.demo.exception;

public class ServiceNotAvailableException extends RuntimeException {
  public ServiceNotAvailableException(String message) {
    super(message);
  }
}
