package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class LoginInvalidInputException extends BusinessException {
    public LoginInvalidInputException() {
        super(ErrorCode.LOGIN_INPUT_INVALID);
    }
}
