package bumblebee.xchangepass.domain.user.repository;

import bumblebee.xchangepass.domain.user.dto.request.UserUpdateRequest;

public interface UserRepositoryCustom {

    // 닉네임 중복 확인
    void checkForDuplicateNickname(String userNickname, Long userId);

    // 전화번호 중복 확인
    void checkForDuplicatePhoneNumber(String userPhoneNumber, Long userId);

    // 유저 정보 수정
    void updateUser(UserUpdateRequest updateRequest, Long userId);

}
