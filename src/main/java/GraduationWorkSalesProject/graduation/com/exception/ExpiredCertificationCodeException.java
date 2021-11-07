package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class ExpiredCertificationCodeException extends BusinessException {
    public ExpiredCertificationCodeException() {
        super(ErrorCode.EXPIRED_CERTIFICATION_CODE);
    }
}
