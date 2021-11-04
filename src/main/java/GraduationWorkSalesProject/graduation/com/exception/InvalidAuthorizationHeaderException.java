package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class InvalidAuthorizationHeaderException extends BusinessException {
    public InvalidAuthorizationHeaderException() {
        super(ErrorCode.INVALID_AUTHORIZATION_HEADER);
    }
}
