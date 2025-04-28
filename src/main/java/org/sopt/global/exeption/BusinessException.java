package org.sopt.global.exeption;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.global.messeage.business.DefaultErrorMessage;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {
    private final DefaultErrorMessage errorMessage;
}
