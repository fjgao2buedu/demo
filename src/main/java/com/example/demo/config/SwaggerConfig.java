package com.example.demo.config;

/**
 * @author fjgao
 * @date 2025/07/03
 */
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER // 明确指定在 header 中
    )
public class SwaggerConfig {

  @Value("${server.port:8080}")
  private String serverPort;

  @Bean
  public OpenAPI customOpenApi() {
    // 定义安全方案要求
    SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

    // 定义安全方案
    io.swagger.v3.oas.models.security.SecurityScheme securityScheme =
        new io.swagger.v3.oas.models.security.SecurityScheme()
            .name("BearerAuth")
            .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER);

    return new OpenAPI()
        .servers(
            List.of(
                new Server().url("http://localhost:" + serverPort).description("Local Dev Server")))
        .info(
            new Info()
                .title("Notes API")
                .version("1.0")
                .description("RESTful API for managing notes and tags")
                .contact(new Contact().name("开发者支持").email("fjgao@bu.edu"))
                .license(
                    new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0")))
        .addSecurityItem(securityRequirement) // 添加全局安全要求
        .components(new Components().addSecuritySchemes("BearerAuth", securityScheme)); // 注册安全方案
  }
}
