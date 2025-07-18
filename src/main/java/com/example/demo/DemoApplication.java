package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author fjgao
 * @since 2025/07/01
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.example.demo"})
@EnableHystrix
public class DemoApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
    Environment env = context.getEnvironment();
    System.out.println("JWT Secret: " + env.getProperty("security.jwt.secret"));
    System.out.println(
        "Feign Timeout: " + env.getProperty("feign.client.config.default.connect-timeout"));
  }
}
