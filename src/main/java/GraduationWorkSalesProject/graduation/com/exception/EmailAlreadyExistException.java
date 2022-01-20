package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class EmailAlreadyExistException extends BusinessException {

    public EmailAlreadyExistException() {
        super(ErrorCode.EMAIL_ALREADY_EXIST);
    }
}
