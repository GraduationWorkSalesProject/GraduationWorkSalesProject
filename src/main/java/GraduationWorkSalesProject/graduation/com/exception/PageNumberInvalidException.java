package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class PageNumberInvalidException extends BusinessException {
    public PageNumberInvalidException () {
        super(ErrorCode.PASSWORD_NOT_MATCH);
    }
}

