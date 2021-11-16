package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class UseridNotFoundException extends BusinessException {
    public UseridNotFoundException() {
        super(ErrorCode.USERID_NOT_FOUND);
    }
}
