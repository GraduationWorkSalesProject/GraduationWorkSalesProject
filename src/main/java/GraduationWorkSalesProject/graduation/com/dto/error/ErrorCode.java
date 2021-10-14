package GraduationWorkSalesProject.graduation.com.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    ARGUMENT_INPUT_INVALID(400, "C001", "유효하지 않은 입력입니다."),
    UNAUTHORIZED(401, "C002", "인증되지 않은 사용자입니다."),
    INTERNAL_SERVER_ERROR(500, "C002", "내부 서버 오류입니다."),

    // Member
    LOGIN_INPUT_INVALID(400, "M001", "아이디 또는 비밀번호가 일치하지 않습니다."),
    JOIN_INPUT_DUPLICATION(400, "M002", "아이디 또는 이메일 또는 닉네임이 이미 존재합니다.")

    ;

    private int status;
    private final String code;
    private final String message;
}
