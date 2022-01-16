package GraduationWorkSalesProject.graduation.com.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "페이징된 검색 상품 결과 조회 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchProductPageRequest {

    @ApiModelProperty(value = "검색 키워드", example = "상품", required = true)
    @NotBlank(message = "검색 키워드는 빈칸이 될 수 없습니다")
    String keyword;

    @ApiModelProperty(value = "정렬 순서", example = "최신순", required = true)
    @NotBlank(message = "정렬 순서를 명시해주세요")
    String sort;

    @ApiModelProperty(value = "페이지 번호", example = "1", required = true)
    @Range(min = 0, message= "페이지 번호는 유효한 값이어야 합니다")
    int page;

    public SearchProductPageRequest(String keyword, String sort, int page){
        this.keyword = keyword;
        this.sort = sort;
        this.page = page;
    }

}
