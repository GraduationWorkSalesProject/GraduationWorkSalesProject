package GraduationWorkSalesProject.graduation.com.dto.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@ApiModel(description = "회원 인증 코드 데이터 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCertificationCodeRequest {

    @ApiModelProperty(value = "인증 코드", example = "123456", required = true)
    @Pattern(regexp = "^[0-9]{6}$", message = "인증 코드 6자리를 정확히 입력해주세요.")
    @NotEmpty(message = "인증 코드는 필수입니다.")
    private String certificationCode;

    @ApiModelProperty(value = "인증 토큰", example = "wjRMbgPxtlKklzV2", required = true)
    @Pattern(regexp = "^[A-Za-z0-9]{16}$", message = "인증 토큰은 숫자, 문자로 구성된 16자리여야 합니다.")
    @NotEmpty(message = "인증 토큰은 필수입니다.")
    private String token;
}
