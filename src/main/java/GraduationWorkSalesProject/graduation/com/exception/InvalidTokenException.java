package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
