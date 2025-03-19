package bumblebee.xchangepass.global.security.v1.login.dto;

import lombok.Builder;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
    @Builder
    public LoginResponse {
    }
}
