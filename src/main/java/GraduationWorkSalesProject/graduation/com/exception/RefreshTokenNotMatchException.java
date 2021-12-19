package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class RefreshTokenNotMatchException extends BusinessException {
    public RefreshTokenNotMatchException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_MATCH);
    }
}
