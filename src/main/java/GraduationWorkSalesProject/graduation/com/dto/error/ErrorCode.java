package GraduationWorkSalesProject.graduation.com.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(500, "C000", "내부 서버 오류입니다."),
    INVALID_INPUT_VALUE(400, "C001", "유효하지 않은 입력입니다."),
    EXPIRED_ACCESS_TOKEN(401, "C002", "만료된 Access Token입니다."),
    EXPIRED_REFRESH_TOKEN(401, "C003", "만료된 Refresh Token입니다."),
    INVALID_JWT(401, "C004", "유효하지 않은 JWT입니다."),
    INVALID_AUTHORIZATION_HEADER(400, "C005", "유효하지 않은 인증 헤더입니다."),
    REFRESH_TOKEN_NOT_MATCH(400, "C006", "Refresh Token이 일치하지 않습니다."),
    SIGNATURE_NOT_MATCH(400, "C006", "JWT의 Signature이 일치하지 않습니다."),
    METHOD_NOT_ALLOWED(405, "C007", "허용되지 않은 HTTP method입니다."),
    INVALID_TYPE_VALUE(400, "C008", "입력 타입이 유효하지 않습니다."),
    INVALID_TOKEN(400, "C009", "유효하지 않은 토큰입니다."),

    // Member
    LOGIN_INPUT_INVALID(400, "M001", "아이디 또는 비밀번호가 일치하지 않습니다."),
    JOIN_INPUT_DUPLICATION(400, "M002", "아이디 또는 이메일 또는 닉네임이 이미 존재합니다."),
    PASSWORD_NOT_MATCH(400, "M003", "새 비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    CERTIFICATION_CODE_NOT_MATCH(400, "M004", "잘못된 인증 코드입니다."),
    INVALID_CERTIFICATE(400, "M005", "유효하지 않은 인증서입니다."),
    EMAIL_NOT_FOUND(400, "M006", "해당 이메일로 가입한 회원은 존재하지 않습니다"),
    USERID_NOT_FOUND(400, "M007", "해당 아이디의 회원은 존재하지 않습니다"),
    USERNAME_NOT_FOUND(400, "M008", "해당 닉네임의 회원은 존재하지 않습니다"),
    EXPIRED_CERTIFICATION_CODE(400, "M009", "만료된 인증 코드입니다."),
    CERTIFY_STUDENT_ENROLL_SUCCESS(400, "M010", "이미 인증요청을 한 상태 입니다."),
    CERTIFY_STUDENT_ENROLL_NOT_FOUND(400, "M011", "학생 인증 등록을 안한 상태입니다."),

    //Product
    PRODUCT_NOT_EXIST(400,"P001","해당 상품이 존재하지 않습니다."),
    PRODUCT_IMAGE_NOT_EXIST(400,"P002","해당 상품이미지가 존재하지 않습니다."),
    HASHTAG_NOT_EXIST(400,"P003","해당 해시태그가 존재하지 않습니다."),
    CATEGORY_NOT_EXIST(400,"P004","해당 카테고리가 존재하지 않습니다."),
    LIKE_NOT_EXIST(400,"P005","회원이 좋아요한 기록이 없습니다."),
    PRODUCT_REGISTER_ACCESS_DENIED(400,"P006","상품을 등록할 수 있는 권한이 없습니다."),

    //Seller
    SELLER_REGISTER_INPUT_DUPLICATION(400, "S001", "해당 닉네임의 회원은 판매자로 이미 존재합니다."),
    SELLER_NOT_FOUND(400, "S002", "해당 닉네임의 회원은 판매자가 아닙니다."),
    ;

    private int status;
    private final String code;
    private final String message;
}
