package org.sopt.global.messeage.business;

import org.springframework.http.HttpStatus;

public enum UserErrorMessage implements DefaultErrorMessage{

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다.");
    private HttpStatus httpStatus;
    private String message;

    UserErrorMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
