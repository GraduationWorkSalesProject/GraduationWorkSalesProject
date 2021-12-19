package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class InvalidCertificateException extends BusinessException {
    public InvalidCertificateException() {
        super(ErrorCode.INVALID_CERTIFICATE);
    }
}
