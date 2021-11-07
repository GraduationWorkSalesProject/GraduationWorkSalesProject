package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class EmailNotExistException extends BusinessException {
    public EmailNotExistException() { super(ErrorCode.EMAIL_NOT_EXIST); }
}
