package bumblebee.xchangepass.domain.user.dto.request;

import bumblebee.xchangepass.domain.user.entity.Sex;
import bumblebee.xchangepass.domain.user.entity.value.UserEmail;
import bumblebee.xchangepass.domain.user.entity.value.UserNickname;
import bumblebee.xchangepass.domain.user.entity.value.UserPhonenumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;


public record UserUpdateRequest(
        @Pattern(regexp = UserNickname.REGEX, message = UserNickname.ERR_MSG)
        String nickName,
        @Pattern(regexp = bumblebee.xchangepass.domain.user.entity.value.UserPhonenumber.REGEX, message = UserPhonenumber.ERR_MSG)
        String phoneNumber,
        @Pattern(regexp = UserEmail.REGEX, message = UserEmail.ERR_MSG)
        String email,
        @NotNull(message = "생년월일은 필수 입력 값입니다.")
        LocalDate birthDay,
        @NotNull(message = "성별은 필수 입력 값입니다.")
        Sex sex
) {
    @Builder
    public UserUpdateRequest {
    }
}
