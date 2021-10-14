package GraduationWorkSalesProject.graduation.com.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "유효하지 않은 입력입니다."),
    INTERNAL_SERVER_ERROR(500, "C002", "내부 서버 오류입니다."),

    // Member
    EMAIL_DUPLICATION(400, "M001", "중복된 이메일입니다."),
    LOGIN_INPUT_INVALID(400, "M002", "이메일 혹은 비밀번호가 일치하지 않습니다.")

    ;
    private int status;
    private final String code;
    private final String message;
}
