package com.example.demo.controller;

import com.example.demo.producer.KafkaProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DemoController {

  private final KafkaProducer producer;

  public DemoController(KafkaProducer producer) {
    this.producer = producer;
  }

  // 发消息：POST http://localhost:8080/api/send?msg=hello
  @PostMapping("/send")
  public String send(@RequestParam String msg) {
    producer.send("test", msg);
    return "发送成功：" + msg;
  }
}
