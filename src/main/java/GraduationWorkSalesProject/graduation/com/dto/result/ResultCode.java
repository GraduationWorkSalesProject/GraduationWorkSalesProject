package GraduationWorkSalesProject.graduation.com.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // Member
    LOGIN_SUCCESS(200, "M100", "로그인에 성공하였습니다."),
    EMAIL_VALID(200, "M101", "사용 가능한 이메일입니다."),
    EMAIL_DUPLICATION(200, "M102", "이미 사용 중인 이메일입니다."),
    JOIN_SUCCESS(200, "M103", "회원 등록 성공"),
    LEAVE_SUCCESS(200, "M104", "회원 탈퇴 성공"),

    ;
    private int status;
    private final String code;
    private final String message;
}
