package com.example.demo.client.fallback;

import com.example.demo.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableHystrix
public class UserClientFallback implements UserClient {

  @Override
  public boolean validateUser(Long userId) {
    // 更简单的错误日志记录
    log.error("用户服务不可用，无法验证用户: {}", userId);

    // 安全策略：拒绝操作
    return false;

    // 或者根据业务需求选择：
    // return true; // 允许操作（风险较高）
  }
}
