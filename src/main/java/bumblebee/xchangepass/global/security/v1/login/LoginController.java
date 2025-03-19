package bumblebee.xchangepass.global.security.v1.login;

import bumblebee.xchangepass.global.security.v1.login.dto.LoginRequest;
import bumblebee.xchangepass.global.security.v1.login.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Login", description = "Login API")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "로그인", description = "로그인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "지갑 생성 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "파라미터 누락",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"S003\"," +
                                                              "\n  \"message\": \"아이디 혹은 비밀번호를 입력하세요.\"}"))
            ),
            @ApiResponse(responseCode = "401", description = "아이디 혹은 비밀번호가 일치하지 않습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"S002\"," +
                                                              "\n  \"message\": \"아이디 혹은 비밀번호가 일치하지 않습니다.\"}"))
            ),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"S001\"," +
                                                              "\n  \"message\": \"권한이 없습니다.\"}"))
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public LoginResponse memberLogin(@RequestBody @Valid LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }
}
