package org.sopt.global.advice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.global.dto.ResponseDTO;
import org.sopt.global.exeption.BusinessException;
import org.sopt.global.messeage.business.BusinessErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ResponseDTO<BusinessErrorMessage>> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(e.getErrorMessage().getHttpStatus())
                .body(ResponseDTO.fail(e.getErrorMessage()));
    }



    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResponseDTO<BusinessErrorMessage>> handleException(Exception e) {
        logger.error("Unhandled exception occurred: {}", e.getMessage(), e);
        return ResponseEntity
                .status(BusinessErrorMessage.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ResponseDTO.fail(BusinessErrorMessage.INTERNAL_SERVER_ERROR));
    }


}