package GraduationWorkSalesProject.graduation.com.dto.certification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@ApiModel(description = "인증 정보 데이터 모델")
@Getter
@AllArgsConstructor
public class CertificationCodeResponse {

    @ApiModelProperty(value = "인증 코드", example = "429882")
    private String certificationCode;

    @ApiModelProperty(value = "만료 일자", example = "2021.10.19. 09:00:23")
    private String expirationDateTime;
}
