package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class UseridNotExistException extends BusinessException {
    public UseridNotExistException() {
        super(ErrorCode.USERID_NOT_EXIST);
    }
}
