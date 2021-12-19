package GraduationWorkSalesProject.graduation.com.exception;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;

public class JoinInvalidInputException extends BusinessException {
    public JoinInvalidInputException() {
        super(ErrorCode.JOIN_INPUT_DUPLICATION);
    }
}
