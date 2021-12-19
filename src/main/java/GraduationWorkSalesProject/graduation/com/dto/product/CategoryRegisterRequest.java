package GraduationWorkSalesProject.graduation.com.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "카테고리 등록 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryRegisterRequest {

    @ApiModelProperty(value = "카테고리명", example = "카테고리예시", required = true)
    @NotEmpty(message = "카테고리이름을 입력해주세요")
    private String categoryName;

    @ApiModelProperty(value = "상위 카테고리 번호", example = "5", required = true)
    @NotEmpty(message = "최상위 카테고리라면 root 카테고리 번호를 입력해주세요")
    private Long categoryParentId;


}
