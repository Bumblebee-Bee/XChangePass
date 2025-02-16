package bumblebee.xchangepass.domain.user.dto.request;

import bumblebee.xchangepass.domain.user.entity.User;
import bumblebee.xchangepass.domain.user.entity.Sex;
import bumblebee.xchangepass.domain.user.entity.value.UserEmail;
import bumblebee.xchangepass.domain.user.entity.value.UserNickname;
import bumblebee.xchangepass.domain.user.entity.value.UserPassword;
import bumblebee.xchangepass.domain.user.entity.value.UserPhonenumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Builder
public record UserRegisterRequest(@Pattern(regexp = UserNickname.REGEX, message = UserNickname.ERR_MSG) String nickName,
                                  @Pattern(regexp = bumblebee.xchangepass.domain.user.entity.value.UserPhonenumber.REGEX, message = UserPhonenumber.ERR_MSG) String phoneNumber,
                                  @Pattern(regexp = UserEmail.REGEX, message = UserEmail.ERR_MSG) String email,
                                  @Pattern(regexp = UserPassword.REGEX, message = UserPassword.ERR_MSG) String pwd,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDay,
                                  @NotNull(message = "성별 입력해주세요") Sex sex) {


    public User toEntity(final PasswordEncoder passwordEncoder){
        return   User.builder()
                .birthDay(birthDay)
                .userSex(sex)
                .userPwd(pwd) //암호화
                .userEmail(email)
                .userPhonenumber(phoneNumber)
                .userNickname(nickName)
                .passwordEncoder(passwordEncoder)
                .build();
    }

}
