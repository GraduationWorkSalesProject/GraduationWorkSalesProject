package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class ProductNotExistException extends BusinessException{
    public ProductNotExistException(){
        super(ErrorCode.PRODUCT_NOT_EXIST);
    }
}
