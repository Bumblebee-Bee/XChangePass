package bumblebee.xchangepass.global.security.v1.refresh;

import bumblebee.xchangepass.global.security.v1.refresh.dto.RefreshTokenRequest;
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
@Tag(name = "RefreshToken", description = "RefreshToken 재발급 API")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "리프레시 토큰 재발급", description = "리프레시 토큰을 재발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "refreshToken 재발급 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "맞지 않는 토큰입니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"S005\"," +
                                                              "\n  \"message\": \"Refresh Token이 만료되었거나 정상적인 Token이 아닙니다.\"}"))
            ),
            @ApiResponse(responseCode = "401", description = "refreshToken이 존재하지 않습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"S004\"," +
                                                              "\n  \"message\": \"refreshToken이 존재하지 않습니다.\"}"))
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/token-refresh")
    public void tokenRefresh(@RequestBody @Valid RefreshTokenRequest request) {
        // token 재발급
        refreshTokenService.refreshToken(request.refreshToken());
    }

}