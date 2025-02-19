package bumblebee.xchangepass.domain.user.dto.request;

import bumblebee.xchangepass.domain.user.entity.Sex;
import bumblebee.xchangepass.domain.user.entity.value.UserName;
import bumblebee.xchangepass.domain.user.entity.value.UserNickname;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;


@Builder
public record UserUpdateRequest(
        @Pattern(regexp = UserName.REGEX, message = UserName.ERR_MSG) String userName,
        @Pattern(regexp = UserNickname.REGEX, message = UserNickname.ERR_MSG) String userNickname,
        @NotNull(message = "나이는 필수 입력 값입니다.") Integer userAge,
        @NotNull(message = "성별은 필수 입력 값입니다.") Sex userSex
) {

}
