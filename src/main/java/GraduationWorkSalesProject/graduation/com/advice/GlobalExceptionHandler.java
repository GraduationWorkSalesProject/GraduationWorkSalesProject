package GraduationWorkSalesProject.graduation.com.advice;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorResponse;
import GraduationWorkSalesProject.graduation.com.exception.JoinInvalidInputException;
import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;
import GraduationWorkSalesProject.graduation.com.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("GraduationWorkSalesProject.graduation.com.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidExceptionHandler(BindException e) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.ARGUMENT_INPUT_INVALID, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> loginInputNotValidExceptionHandler(MemberNotFoundException e) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.LOGIN_INPUT_INVALID);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> joinInputDuplicationExceptionHandler(JoinInvalidInputException e) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.JOIN_INPUT_DUPLICATION);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
