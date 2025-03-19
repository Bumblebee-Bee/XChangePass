package bumblebee.xchangepass.global.security.v1.login.dto;

public record LoginRequest(
        String userEmail,
        String password
) {
}
