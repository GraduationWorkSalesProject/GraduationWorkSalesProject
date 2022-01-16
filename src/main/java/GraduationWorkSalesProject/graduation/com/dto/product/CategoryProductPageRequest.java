package GraduationWorkSalesProject.graduation.com.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "페이징된 카테고리내 상품 목록 조회 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryProductPageRequest {

    @ApiModelProperty(value = "카테고리 아이디", example = "1", required = true)
    @Range(min = 1, message= "카테고리 번호는 유효한 값이어야 합니다")
    Long categoryId;

    @ApiModelProperty(value = "정렬 순서", example = "최신순", required = true)
    @NotBlank(message = "정렬 순서를 명시해주세요")
    String sort;

    @ApiModelProperty(value = "페이지 번호", example = "1", required = true)
    @Range(min = 0, message= "페이지 번호는 유효한 값이어야 합니다")
    int page;

    public CategoryProductPageRequest(Long categoryId, String sort, int page){
        this.categoryId = categoryId;
        this.sort = sort;
        this.page = page;
    }
}
