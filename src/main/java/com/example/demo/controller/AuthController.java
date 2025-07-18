package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;

  // 通过构造函数注入依赖
  public AuthController(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      JwtTokenProvider tokenProvider) {

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
  }

  @PostMapping("/login")
  public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
    // 验证用户凭证
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

    // 设置认证信息
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 生成 JWT
    String jwt = tokenProvider.generateToken(authentication);

    return new JwtResponse(jwt);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
    // 检查用户名是否已存在
    if (userRepository.existsUserByUsername(registerRequest.getUsername())) {
      return ResponseEntity.badRequest().body("错误：用户名已被使用");
    }

    // 创建新用户并加密密码
    User user = new User();
    user.setUsername(registerRequest.getUsername());

    // 使用BCrypt加密密码
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

    userRepository.save(user);

    return ResponseEntity.ok("用户注册成功");
  }
}
