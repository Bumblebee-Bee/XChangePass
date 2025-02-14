package bumblebee.xchangepass.domain.user.controller;

import bumblebee.xchangepass.domain.user.dto.CustomUserDetails;
import bumblebee.xchangepass.domain.user.dto.request.UserRegisterRequest;
import bumblebee.xchangepass.domain.user.dto.request.UserUpdateRequest;
import bumblebee.xchangepass.domain.user.dto.response.UserResponse;
import bumblebee.xchangepass.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 등록
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void signup(@RequestBody @Valid UserRegisterRequest request) {
        userService.signupUser(request);
    }

    // 내 정보 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public UserResponse read(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return userService.read(customUserDetails.getId());
    }


    // 내 정보 수정
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping()
    public UserResponse update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid UserUpdateRequest request) {
        return userService.update(customUserDetails.getId(), request);
    }

    // 내 정보 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    public void delete(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        userService.delete(customUserDetails.getId());
    }
}
