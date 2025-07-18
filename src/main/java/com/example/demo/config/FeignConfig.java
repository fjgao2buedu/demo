package com.example.demo.config;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignConfig {
  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      // 打印请求详细信息
      System.out.println("======= Feign 请求信息 =======");
      System.out.println("URL: " + requestTemplate.url());
      System.out.println("Method: " + requestTemplate.method());
      System.out.println("Headers: " + requestTemplate.headers());

      // 打印安全上下文信息
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      System.out.println("Security Context: " + (auth != null ? auth.toString() : "null"));

      // 添加认证头
      if (auth != null && auth.getCredentials() instanceof String token) {
        System.out.println("Adding Token: Bearer " + token.substring(0, 20) + "...");
        requestTemplate.header("Authorization", "Bearer " + token);
      } else {
        System.out.println("No valid credentials found");
      }
    };
  }
}
