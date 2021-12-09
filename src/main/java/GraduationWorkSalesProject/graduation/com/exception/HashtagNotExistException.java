package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class HashtagNotExistException extends BusinessException{
    public HashtagNotExistException(){
        super(ErrorCode.HASHTAG_NOT_EXIST);
    }
}
