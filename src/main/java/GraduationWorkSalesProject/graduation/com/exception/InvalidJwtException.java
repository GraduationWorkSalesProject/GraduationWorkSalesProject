package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class InvalidJwtException extends BusinessException {
    public InvalidJwtException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
