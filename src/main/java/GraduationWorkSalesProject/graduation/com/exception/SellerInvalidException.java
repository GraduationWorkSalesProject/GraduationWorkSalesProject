package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class SellerInvalidException extends BusinessException {
    public SellerInvalidException() {
        super(ErrorCode.SELLER_REGISTER_INPUT_DUPLICATION);
    }
}
