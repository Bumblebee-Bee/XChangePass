package bumblebee.xchangepass.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerOpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("XChangePass API")
                        .description("실시간 환율 및 다중 통화 관리 서비스 API 문서")
                        .version("1.0.0"))
                .servers(List.of(
                new Server().url("http://localhost:8080/").description("Staging ENV")
                ));
    }

}
