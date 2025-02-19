package bumblebee.xchangepass.global.error;

import bumblebee.xchangepass.global.exception.CommonException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /*User*/
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "U001", "존재 하지 않는 회원입니다."),
    USER_UPDATE_EXCEPTION(HttpStatus.BAD_REQUEST, "U002", "잘못된 회원 수정 입니다."),
    USER_DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "U003", "중복된 이메일 입니다."),
    USER_DUPLICATE_NICK_NAME(HttpStatus.BAD_REQUEST, "U004", "중복된 닉네임 입니다."),
    USER_DUPLICATE_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "U005", "중복된 전화번호 입니다."),
    USER_NOT_MODIFY(HttpStatus.BAD_REQUEST,"U006","회원 수정 실패"),

    /*기타*/
    ENTITY_FIELD_ACCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G001", "엔티티 필드 접근 오류");

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
