package org.sopt.global.advice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.global.dto.ResponseDTO;
import org.sopt.global.exeption.BusinessException;
import org.sopt.global.messeage.business.BusinessErrorMessage;
import org.sopt.global.messeage.business.CommonErrorMessage;
import org.sopt.global.messeage.business.DefaultErrorMessage;
import org.sopt.global.messeage.business.PostErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ResponseDTO<Void>> handleBusinessException(BusinessException e) {
        DefaultErrorMessage errorMessage = e.getErrorMessage();
        return ResponseEntity
                .status(errorMessage.getHttpStatus())
                .body(ResponseDTO.fail(errorMessage));
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResponseDTO<Void>> handleException(Exception e) {
        logger.error("Unhandled exception occurred: {}", e.getMessage(), e);
        return ResponseEntity
                .status(CommonErrorMessage.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ResponseDTO.fail(CommonErrorMessage.INTERNAL_SERVER_ERROR));
    }
}
