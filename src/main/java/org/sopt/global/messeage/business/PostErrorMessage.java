package org.sopt.global.messeage.business;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


public enum PostErrorMessage implements DefaultErrorMessage{

    POST_TITLE_DUPLICATE(HttpStatus.BAD_REQUEST, "게시물 제목이 중복됩니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다."),
    POST_TIME_OUT(HttpStatus.BAD_REQUEST, "시간 제한을 초과했습니다. 잠시 후에 다시 시도해주세요."),
    POST_CREATION_TIME_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "게시물을 작성할 수 있는 최소 시간 간격을 초과했습니다."),
    INVALID_TITLE_LENGTH(HttpStatus.BAD_REQUEST,"게시물 제목 길이를 확인해주세요");
    private HttpStatus httpStatus;
    private String message;

    PostErrorMessage(HttpStatus httpStatus, String message) {
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
