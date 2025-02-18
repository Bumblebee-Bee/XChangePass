package bumblebee.xchangepass.global.util;

import bumblebee.xchangepass.global.error.ErrorCode;

import java.util.function.BiPredicate;

public class DuplicateCheckUtil {

    /**
     * 중복 검증 공통 로직
     *
     * @param duplicateChecker 중복 검사 로직을 담은 BiPredicate
     * @param value 검사할 값
     * @param userId 사용자 ID (중복 검사를 제외할 ID)
     * @param errorCode 중복이 발견되었을 때 던질 예외 코드
     * @param <T> 값의 타입
     */
    public static <T> void checkDuplicate(
            BiPredicate<T, Long> duplicateChecker,
            T value,
            Long userId,
            ErrorCode errorCode
    ) {
        if (duplicateChecker.test(value, userId)) {
            throw errorCode.commonException();
        }
    }
}
