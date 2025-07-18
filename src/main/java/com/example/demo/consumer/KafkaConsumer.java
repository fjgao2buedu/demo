package com.example.demo.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

  @KafkaListener(topics = "test", groupId = "demo-group")
  public void listen(String message) {
    System.out.println("收到消息: " + message);
  }
}
