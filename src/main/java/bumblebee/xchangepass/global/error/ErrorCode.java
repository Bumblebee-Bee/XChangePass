package bumblebee.xchangepass.global.error;

import bumblebee.xchangepass.global.exception.CommonException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /*Member*/
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "M003-1", "존재 하지 않는 회원입니다."),
    USER_UPDATE_EXCEPTION(HttpStatus.BAD_REQUEST, "M001", "잘못된 회원 수정 입니다."),
    USER_DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "L005", "중복된 이메일 입니다."),
    USER_DUPLICATE_NICK_NAME(HttpStatus.BAD_REQUEST, "L006", "중복된 닉네임 입니다."),
    USER_DUPLICATE_PHONENUMBER(HttpStatus.BAD_REQUEST, "L007", "중복된 전화번호 입니다."),
    USER_NOT_MODIFY(HttpStatus.BAD_REQUEST,"L008","회원 수정 실패");

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(HttpStatus status,String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public CommonException commonException() {
        return new CommonException(this);
    }

}
