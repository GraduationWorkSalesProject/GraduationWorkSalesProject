package GraduationWorkSalesProject.graduation.com.dto.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "회원 닉네임 중복 체크 데이터 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUsernameCheckRequest {

    @ApiModelProperty(value = "닉네임", example = "만두", required = true)
    @Length(min = 2, max = 10, message = "닉네임은 2자리 이상, 10자리 이하로 입력해주세요.")
    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String username;
}
