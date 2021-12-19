package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class CertificationCodeNotMatchException extends BusinessException {
    public CertificationCodeNotMatchException() {
        super(ErrorCode.CERTIFICATION_CODE_NOT_MATCH);
    }
}
