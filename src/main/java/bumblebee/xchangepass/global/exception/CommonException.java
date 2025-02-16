package bumblebee.xchangepass.global.exception;

import bumblebee.xchangepass.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{
    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
