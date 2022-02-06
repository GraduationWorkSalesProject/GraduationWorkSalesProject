package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class NotFollowedException extends BusinessException {
	public NotFollowedException() {
        super(ErrorCode.NOT_FOLLOWED);
    }
}

