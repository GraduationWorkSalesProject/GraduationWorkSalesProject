package GraduationWorkSalesProject.graduation.com.dto.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ApiModel(description = "회원 아이디 찾기 데이터 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberHelpFindUseridRequest {

    @ApiModelProperty(value = "이메일", example = "example@gmail.com", required = true)
    @Email(message = "이메일 형식이 잘못되었습니다.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    String email;

    @ApiModelProperty(value = "인증 토큰", example = "wjRMbgPxtlKklzV2", required = true)
    @Length(min = 16, max = 16, message = "인증 토큰은 16자리입니다.")
    @NotEmpty(message = "인증 토큰은 필수입니다.")
    private String token;
}
