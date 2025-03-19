package bumblebee.xchangepass.global.security.v1.refresh.dto;

import lombok.Builder;

public record RefreshTokenResponse(
        String accessToken, String refreshToken
) {
    @Builder
    public RefreshTokenResponse {
    }
}
