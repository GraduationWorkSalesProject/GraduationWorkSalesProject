package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class ProductImageNotExistException extends BusinessException{
    public ProductImageNotExistException(){ super(ErrorCode.PRODUCT_IMAGE_NOT_EXIST); }
}
