package GraduationWorkSalesProject.graduation.com.dto.member;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "학생 인증 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberStudentCertificationRequest {

	@ApiModelProperty(value = "대학교", example = "서울대학교", required = true)
    @NotBlank(message = "학교 이름을 입력해 주세요")
	private String school;

	@ApiModelProperty(value = "학과", example = "디지털공예학과", required = true)
    @NotBlank(message = "학과 이름을 입력해 주세요")
	private String department;

	@ApiModelProperty(value = "인증 사진", required = true)
    @NotBlank(message = "학교 이름을 입력해 주세요")
	private MultipartFile image;

}
