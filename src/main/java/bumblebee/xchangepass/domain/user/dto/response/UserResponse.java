package bumblebee.xchangepass.domain.user.dto.response;


import bumblebee.xchangepass.domain.user.entity.Sex;
import bumblebee.xchangepass.domain.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record UserResponse(String nickName,
                           String phoneNumber,
                           LocalDate birthDay,
                           Sex sex,
                           String email,
                           String pwd,
                           LocalDateTime createAt){



    public UserResponse(User user) {
        this( user.getUserNickname(),
        user.getUserPhonenumber(),
        user.getBirthDay(),
        user.getUserSex(),
        user.getUserEmail(),
        user.getUserPwd(),
        LocalDateTime.now()
        );
    }

}
