package GraduationWorkSalesProject.graduation.com.dto.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ApiModel(value = "회원 이메일 중복 체크 데이터 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEmailCheckRequest {

    @ApiModelProperty(value = "이메일", example = "example@gmail.com", required = true)
    @Email(message = "이메일 형식이 잘못되었습니다.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    String email;
}
