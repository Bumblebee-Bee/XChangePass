package bumblebee.xchangepass.domain.user.dto.response;

import bumblebee.xchangepass.domain.user.entity.Role;
import bumblebee.xchangepass.domain.user.entity.User;
import lombok.Builder;

public record UserLoginResponse(
        Long userId,
        String userEmail,
        String password,
        String name,
        String tel,
        Role role
) {
    @Builder
    public UserLoginResponse {
    }

    public static UserLoginResponse fromEntity(User user) {
        return new UserLoginResponse(
                user.getUserId(),
                user.getUserEmail().getValue(),
                user.getUserPwd().getValue(),
                user.getUserName().getValue(),
                user.getUserPhoneNumber().getValue(),
                user.getUserType()
        );
    }
}
