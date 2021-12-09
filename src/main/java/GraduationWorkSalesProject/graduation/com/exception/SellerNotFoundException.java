package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class SellerNotFoundException extends BusinessException {
	 public SellerNotFoundException() {
	        super(ErrorCode.SELLER_NOT_FOUND);
	    }
}
