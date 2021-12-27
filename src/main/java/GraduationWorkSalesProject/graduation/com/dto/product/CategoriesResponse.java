package GraduationWorkSalesProject.graduation.com.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@ApiModel(value = "카테고리 리스트 모델")
@Getter
@AllArgsConstructor
public class CategoriesResponse {

    @ApiModelProperty(value = "카테고리 리스트")
    private List<CategoryResponse> categoryList;

}
