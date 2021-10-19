package GraduationWorkSalesProject.graduation.com.advice;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorResponse;
import GraduationWorkSalesProject.graduation.com.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice("GraduationWorkSalesProject.graduation.com.controller")
public class GlobalExceptionHandler {

    // TODO: Exception 상속화하여 Business Exception Handler 하나로 통합
    //  github 참고해서 설계해보기

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidExceptionHandler(BindException e) {
        ErrorResponse response = ErrorResponse.of(ARGUMENT_INPUT_INVALID, e.getBindingResult());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> loginInputNotValidExceptionHandler(LoginInvalidInputException e) {
        ErrorResponse response = ErrorResponse.of(LOGIN_INPUT_INVALID);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> joinInputDuplicationExceptionHandler(JoinInvalidInputException e) {
        ErrorResponse response = ErrorResponse.of(JOIN_INPUT_DUPLICATION);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> passwordNotMatchExceptionHandler(PasswordNotMatchException e) {
        ErrorResponse response = ErrorResponse.of(PASSWORD_NOT_MATCH);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> certificationCodeNotMatchExceptionHandler(CertificationCodeNotMatchException e) {
        final ErrorResponse response = ErrorResponse.of(CERTIFICATION_CODE_NOT_MATCH);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> invalidCertificateExceptionHandler(InvalidCertificateException e) {
        final ErrorResponse response = ErrorResponse.of(INVALID_CERTIFICATE);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> emailNotExistExceptionHandler(EmailNotExistException e) {
        final ErrorResponse response = ErrorResponse.of(EMAIL_NOT_EXIST);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> useridNotExistExceptionHandler(UseridNotExistException e) {
        final ErrorResponse response = ErrorResponse.of(USERID_NOT_EXIST);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = ErrorResponse.of(INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
