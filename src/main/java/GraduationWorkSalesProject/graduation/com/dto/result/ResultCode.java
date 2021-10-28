package GraduationWorkSalesProject.graduation.com.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // Common
    REISSUE_SUCCESS(200,"C100", "Access Token 재발급 성공"),

    // Member
    LOGIN_SUCCESS(200, "M100", "로그인에 성공하였습니다."),
    EMAIL_VALID(200, "M101", "사용 가능한 이메일입니다."),
    USERID_VALID(200, "M102", "사용 가능한 아이디입니다."),
    USERNAME_VALID(200, "M103", "사용 가능한 닉네임입니다."),
    JOIN_SUCCESS(200, "M104", "회원 등록 성공"),
    LEAVE_SUCCESS(200, "M105", "회원 탈퇴 성공"),
    SEND_MAIL_SUCCESS(200, "M106", "메일 전송이 완료되었습니다."),
    EMAIL_DUPLICATION(200, "M107", "이미 사용중인 이메일입니다."),
    USERID_DUPLICATION(200, "M108", "이미 사용중인 아이디입니다."),
    USERNAME_DUPLICATION(200, "M109", "이미 사용중인 닉네임입니다."),
    FIND_USERID_SUCCESS(200, "M110", "회원 아이디 찾기 성공"),
    CHANGE_PASSWORD_SUCCESS(200, "M111", "회원 비밀번호 변경 성공"),
    CERTIFY_EMAIL_SUCCESS(200, "M112", "회원 이메일 인증 성공")
    ;

    private int status;
    private final String code;
    private final String message;
}
