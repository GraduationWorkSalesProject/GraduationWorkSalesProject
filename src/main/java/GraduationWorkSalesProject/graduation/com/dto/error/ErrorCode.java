package GraduationWorkSalesProject.graduation.com.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(500, "C000", "내부 서버 오류입니다."),
    ARGUMENT_INPUT_INVALID(400, "C001", "유효하지 않은 입력입니다."),
    EXPIRED_ACCESS_TOKEN(401, "C002", "만료된 Access Token입니다."),
    EXPIRED_REFRESH_TOKEN(401, "C003", "만료된 Refresh Token입니다."),
    INVALID_TOKEN(401, "C004", "유효하지 않은 토큰입니다."),
    INVALID_AUTHORIZATION_HEADER(400, "C005", "유효하지 않은 인증 헤더입니다."),
    REFRESH_TOKEN_NOT_MATCH(400, "C006", "Refresh Token이 일치하지 않습니다."),
    SIGNATURE_NOT_MATCH(400, "C006", "JWT의 Signature이 일치하지 않습니다."),

    // Member
    LOGIN_INPUT_INVALID(400, "M001", "아이디 또는 비밀번호가 일치하지 않습니다."),
    JOIN_INPUT_DUPLICATION(400, "M002", "아이디 또는 이메일 또는 닉네임이 이미 존재합니다."),
    PASSWORD_NOT_MATCH(400, "M003", "새 비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    CERTIFICATION_CODE_NOT_MATCH(400, "M004", "잘못된 인증 코드입니다."),
    INVALID_CERTIFICATE(400, "M005", "유효하지 않은 인증서입니다."),
    EMAIL_NOT_EXIST(400, "M006", "해당 이메일로 가입한 회원은 존재하지 않습니다"),
    USERID_NOT_EXIST(400, "M007", "해당 아이디는 존재하지 않습니다"),
    EXPIRED_CERTIFICATION_CODE(400, "M005", "만료된 인증 코드입니다."),
    ;

    private int status;
    private final String code;
    private final String message;
}
