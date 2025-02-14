package bumblebee.xchangepass.domain.user.service;

import bumblebee.xchangepass.domain.user.dto.CustomUserDetails;
import bumblebee.xchangepass.domain.user.entity.User;
import bumblebee.xchangepass.domain.user.repository.UserRepository;
import bumblebee.xchangepass.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(ErrorCode.USER_NOT_FOUND::commonException);

        if (user != null){

            return new CustomUserDetails(user);
        }
        return null;
    }
}
