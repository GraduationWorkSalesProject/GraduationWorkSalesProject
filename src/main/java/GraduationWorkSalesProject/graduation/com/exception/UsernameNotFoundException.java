package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class UsernameNotFoundException extends BusinessException{

    public UsernameNotFoundException() {
        super(ErrorCode.USERNAME_NOT_FOUND);
    }
}
