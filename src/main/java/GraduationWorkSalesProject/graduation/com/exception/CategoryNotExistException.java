package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class CategoryNotExistException extends BusinessException{
    public CategoryNotExistException(){
        super(ErrorCode.CATEGORY_NOT_EXIST);
    }
}
