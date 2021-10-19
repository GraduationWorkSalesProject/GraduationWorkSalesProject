package GraduationWorkSalesProject.graduation.com.dto.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "회원 아이디 중복 체크 데이터 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUseridCheckRequest {

    @ApiModelProperty(value = "아이디", example = "example123", required = true)
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String userid;
}
