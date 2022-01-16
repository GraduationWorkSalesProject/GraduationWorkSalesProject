package GraduationWorkSalesProject.graduation.com.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = "회원 정보 수정 모델")
@Getter
public class MemeberProfileEditRequest {
    @ApiModelProperty(value = "휴대폰번호", example = "010-1234-5678", required = true)
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호는 010-1234-5678 형태로 입력해주세요.")
    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String phoneNumber;

    @ApiModelProperty(value = "주소", example = "경기 성남시 분당구 판교역로 235", required = true)
    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @ApiModelProperty(value = "상세주소", example = "에이치스퀘어 N동 7층", required = true)
    @NotBlank(message = "상세주소를 입력해주세요.")
    private String detailAddress;

    @ApiModelProperty(value = "우편번호", example = "30872", required = true)
    @NotBlank(message = "우편번호를 입력해주세요.")
    private String postcode;

}
