package com.example.demo.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
  private final KafkaTemplate<String, String> template;

  public KafkaProducer(KafkaTemplate<String, String> template) {
    this.template = template;
  }

  public void send(String topic, String payload) {
    template.send(topic, payload);
  }
}
