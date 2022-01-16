package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class StudentCertificationNotFoundException extends BusinessException {
    public StudentCertificationNotFoundException() {
        super(ErrorCode.CERTIFY_STUDENT_ENROLL_NOT_FOUND);
    }
}
