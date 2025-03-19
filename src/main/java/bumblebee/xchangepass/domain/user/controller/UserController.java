package bumblebee.xchangepass.domain.user.controller;

import bumblebee.xchangepass.domain.user.dto.request.UserRegisterRequest;
import bumblebee.xchangepass.domain.user.dto.request.UserUpdateRequest;
import bumblebee.xchangepass.domain.user.dto.response.UserResponse;
import bumblebee.xchangepass.domain.user.service.UserService;
import bumblebee.xchangepass.global.security.jwt.JwtUtil;
import bumblebee.xchangepass.global.security.v1.login.UserRegisterService;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "User management API")
public class UserController {

    private final UserService userService;
    private final UserRegisterService registerService;

    @Operation(summary = "회원 등록", description = "새로운 사용자를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "회원가입 실패",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = "{\n  \"code\": \"U003\"," +
                                                       "\n  \"message\": \"회원가입에 실패했습니다.\"," +
                                                       "\n  \"validation\": {\n    \"email\": \"이메일 형식이 올바르지 않습니다.\"\n  }\n}"))
            ),
            @ApiResponse(responseCode = "500", description = "엔티티 필드 접근 오류",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = "{\n  \"code\": \"G001\"," +
                                                       "\n  \"message\": \"엔티티 필드 접근 오류.\"}"))
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void signup(@RequestBody @Valid UserRegisterRequest request) {
        registerService.signupUser(request);
    }


    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "내 정보 조회 실패",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"U001\"," +
                                                               "\n  \"message\": \"존재 하지 않는 회원입니다.\"\n}"))
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public UserResponse read(Authentication authentication){
        return userService.readUser(JwtUtil.getLoginId(authentication));
    }


    @Operation(summary = "내 정보 수정", description = "현재 로그인한 사용자의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "회원가입 실패",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"U002\"," +
                                                               "\n  \"message\": \"회원 수정에 실패했습니다.\"," +
                                                               "\n  \"validation\": {\n    \"email\": \"이메일 형식이 올바르지 않습니다.\"\n  }\n}"))
            ),
            @ApiResponse(responseCode = "500", description = "엔티티 필드 접근 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"G001\"," +
                                                               "\n  \"message\": \"엔티티 필드 접근 오류.\"}"))
            )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void update(Authentication authentication,
                       @RequestBody @Valid UserUpdateRequest request) {
        userService.updateUser(JwtUtil.getLoginId(authentication), request);
    }


    @Operation(summary = "회원탈퇴 요청", description = "현재 로그인한 사용자의 회원탈퇴를 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "회원탈퇴 요청 실패",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"U001\"," +
                                                               "\n  \"message\": \"존재 하지 않는 회원입니다.\"}"))
            )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void softDeleteUser(Authentication authentication) {
        userService.softDeleteUser(JwtUtil.getLoginId(authentication));
    }
}
