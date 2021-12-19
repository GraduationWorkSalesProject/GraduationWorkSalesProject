package GraduationWorkSalesProject.graduation.com.dto.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "JWT 토큰 재발급 데이터 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJwtTokenRequest {

    @ApiModelProperty(value = "Access Token", example = "AAA.BBB.CCC", required = true)
    @NotEmpty(message = "Access Token을 입력해주세요.")
    private String accessToken;

    @ApiModelProperty(value = "Refresh Token", example = "XXX.YYY.ZZZ", required = true)
    @NotEmpty(message = "Refresh Token을 입력해주세요.")
    private String refreshToken;
}
