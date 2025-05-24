package org.sopt.global.messeage.business;

import org.springframework.http.HttpStatus;

// 공통 에러 메시지 정의
public enum CommonErrorMessage implements DefaultErrorMessage {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final HttpStatus status;
    private final String message;

    CommonErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
