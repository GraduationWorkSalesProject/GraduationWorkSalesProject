package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class EmailNotFoundException extends BusinessException {
    public EmailNotFoundException() { super(ErrorCode.EMAIL_NOT_FOUND); }
}
