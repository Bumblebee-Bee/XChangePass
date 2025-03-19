package bumblebee.xchangepass.domain.user.service;

import bumblebee.xchangepass.domain.user.dto.request.UserRegisterRequest;
import bumblebee.xchangepass.domain.user.dto.response.UserLoginResponse;
import bumblebee.xchangepass.domain.user.entity.Role;
import bumblebee.xchangepass.domain.user.entity.Sex;
import bumblebee.xchangepass.domain.user.repository.UserRepository;
import bumblebee.xchangepass.global.security.jwt.JwtProvider;
import bumblebee.xchangepass.global.security.v1.login.LoginService;
import bumblebee.xchangepass.global.security.v1.login.UserRegisterService;
import bumblebee.xchangepass.global.security.v1.login.dto.LoginRequest;
import bumblebee.xchangepass.global.security.v1.login.dto.LoginResponse;
import bumblebee.xchangepass.global.security.v1.refresh.RefreshToken;
import bumblebee.xchangepass.global.security.v1.refresh.RefreshTokenService;
import bumblebee.xchangepass.global.security.v1.refresh.dto.RefreshTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLoginScenarioTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private NicknameGenerator nicknameGenerator;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRegisterService userRegisterService;

    @InjectMocks
    private LoginService loginService;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    private UserRegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private String refreshToken;

    @BeforeEach
    void setUp() {
        registerRequest = new UserRegisterRequest("test@example.com", "Password123!", "test", "010-1234-5678", Sex.MALE, "1234");
        loginRequest = new LoginRequest("test@example.com", "Password123");
        refreshToken = "validRefreshToken";

        // 회원가입 시 닉네임 생성
        when(nicknameGenerator.generateUniqueNickname()).thenReturn("testUser");

        // 비밀번호 암호화 Mock
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("hashedPassword");

        // 로그인 시 사용자 조회
        UserLoginResponse userInfo = new UserLoginResponse(1L, "test@example.com", "hashedPassword", "testUser", "010-1234-5678", Role.ROLE_USER);
        when(userService.readUserByUserEmail(loginRequest.userEmail())).thenReturn(userInfo);
        when(bCryptPasswordEncoder.matches(loginRequest.password(), userInfo.password())).thenReturn(true);

        // JWT 발급 Mock
        when(jwtProvider.generateAccessToken(1L)).thenReturn("accessToken");
        when(jwtProvider.generateRefreshToken(1L)).thenReturn("refreshToken");

        // Refresh Token 검증 Mock
        when(jwtProvider.validateToken(refreshToken)).thenReturn(true);
    }

    @Test
    void 회원가입_로그인_Refresh토큰_재발급_테스트() {
        // 1️⃣ 회원가입
        assertDoesNotThrow(() -> userRegisterService.signupUser(registerRequest));
        verify(nicknameGenerator, times(1)).generateUniqueNickname();
        verify(userRepository, times(1)).save(any());

        // 2️⃣ 로그인 → AccessToken, RefreshToken 발급 확인
        LoginResponse loginResponse = loginService.login(loginRequest);

        assertNotNull(loginResponse);
        assertEquals("accessToken", loginResponse.accessToken());
        assertEquals("refreshToken", loginResponse.refreshToken());

        verify(userService, times(1)).readUserByUserEmail(loginRequest.userEmail());
        verify(jwtProvider, times(1)).generateAccessToken(1L);
        verify(jwtProvider, times(1)).generateRefreshToken(1L);

        // 3️⃣ Refresh Token 저장 (테스트 환경에서 직접 추가)
        RefreshToken.putRefreshToken(refreshToken, 1L);

        // 4️⃣ Refresh Token을 이용하여 새로운 AccessToken, RefreshToken 발급
        RefreshTokenResponse refreshResponse = refreshTokenService.refreshToken(refreshToken);

        assertNotNull(refreshResponse);
        assertEquals("accessToken", refreshResponse.accessToken());
        assertEquals("refreshToken", refreshResponse.refreshToken());

        verify(jwtProvider, times(1)).validateToken(refreshToken);
        verify(jwtProvider, times(2)).generateAccessToken(1L);
        verify(jwtProvider, times(2)).generateRefreshToken(1L);
    }
}
