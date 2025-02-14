package bumblebee.xchangepass.domain.user.service;

import bumblebee.xchangepass.domain.user.dto.request.UserRegisterRequest;
import bumblebee.xchangepass.domain.user.dto.request.UserUpdateRequest;
import bumblebee.xchangepass.domain.user.dto.response.UserResponse;
import bumblebee.xchangepass.domain.user.entity.User;
import bumblebee.xchangepass.domain.user.repository.UserRepository;
import bumblebee.xchangepass.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signupUser(UserRegisterRequest request) {
        Optional<User> byUserEmail =
                userRepository.findByUserEmail(request.email());
        boolean present = byUserEmail.isPresent();

        if (present) {
            throw ErrorCode.USER_DUPLICATE_EMAIL.commonException();
        }

        userRepository.save(request.toEntity(bCryptPasswordEncoder));
    }

    public UserResponse read(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ErrorCode.USER_NOT_FOUND::commonException);

        return new UserResponse(user);
    }

    public UserResponse update(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(ErrorCode.USER_NOT_FOUND::commonException);

        try {
            user.updateUser(request);
            userRepository.save(user);
        } catch (Exception e) {
            throw ErrorCode.USER_UPDATE_EXCEPTION.commonException();
        }
        return new UserResponse(user);
    }

    public void delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ErrorCode.USER_NOT_FOUND::commonException);

        userRepository.delete(user);
    }
}
