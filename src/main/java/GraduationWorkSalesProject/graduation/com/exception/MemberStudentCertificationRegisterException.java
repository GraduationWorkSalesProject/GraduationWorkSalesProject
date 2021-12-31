package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class MemberStudentCertificationRegisterException extends BusinessException {
    public MemberStudentCertificationRegisterException() {
        super(ErrorCode.CERTIFY_STUDENT_ENROLL_SUCCESS);
    }
}
