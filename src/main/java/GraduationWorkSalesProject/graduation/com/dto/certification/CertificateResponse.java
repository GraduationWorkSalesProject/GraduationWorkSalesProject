package GraduationWorkSalesProject.graduation.com.dto.certification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApiModel(description = "인증서 데이터 모델")
@Getter
@AllArgsConstructor
public class CertificateResponse {

    @ApiModelProperty(value = "인증 토큰", example = "wjRMbgPxtlKklzV2")
    private String token;

    @ApiModelProperty(value = "만료 일자", example = "2021.10.19. 09:00:23")
    private String expirationDateTime;
}
