package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class ProductRegisterCertificationException extends BusinessException{
    public ProductRegisterCertificationException() {
        super(ErrorCode.PRODUCT_REGISTER_ACCESS_DENIED);
    }
}
