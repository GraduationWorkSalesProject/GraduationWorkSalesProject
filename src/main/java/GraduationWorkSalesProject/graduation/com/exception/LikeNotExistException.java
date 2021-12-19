package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class LikeNotExistException extends BusinessException{
    public LikeNotExistException(){
        super(ErrorCode.LIKE_NOT_EXIST);
    }
}
