package com.jyang.boardserver.exception.handler;

import com.jyang.boardserver.dto.response.CommonResponse;
import com.jyang.boardserver.exception.BoardServerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse<String>> handleRuntimeException(RuntimeException e) {
        CommonResponse<String> commonResponse = new CommonResponse<>(HttpStatus.OK, "RuntimeException", e.getMessage(),
                e.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }

    @ExceptionHandler(BoardServerException.class)
    public ResponseEntity<CommonResponse<String>> handleBoardServerException(BoardServerException e) {
        CommonResponse<String> commonResponse = new CommonResponse<>(e.getHttpStatus(), "BoardServerException",
                e.getMessage(),
                e.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<String>> handleException(Exception e) {
        CommonResponse<String> commonResponse = new CommonResponse<>(HttpStatus.OK, "Exception",
                e.getMessage(),
                e.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }
}