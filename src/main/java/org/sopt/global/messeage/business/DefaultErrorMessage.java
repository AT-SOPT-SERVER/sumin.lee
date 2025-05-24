package org.sopt.global.messeage.business;

import org.springframework.http.HttpStatus;

public interface DefaultErrorMessage {
    HttpStatus getHttpStatus();
    String getMessage();
}

//인터페이스 vs 구현체

