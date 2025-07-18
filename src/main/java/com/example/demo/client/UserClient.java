package com.example.demo.client;

import com.example.demo.client.fallback.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "user-service",
    url = "${user-service.url}",
    fallback = UserClientFallback.class)
public interface UserClient {

  @GetMapping("/users/{userId}/validate")
  boolean validateUser(@PathVariable Long userId);
}
