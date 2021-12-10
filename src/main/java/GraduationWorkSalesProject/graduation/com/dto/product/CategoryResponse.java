package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.product.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel(value = "개별 카테고리 모델")
public class CategoryResponse {

    @ApiModelProperty(value = "카테고리 아이디")
    private Long categoryId;

    @ApiModelProperty(value = "카테고리명")
    private String categoryName;

    @ApiModelProperty(value = "상위 카테고리 번호", notes = "가장 상위 카테고리명은 root")
    private Long parentId;

    public CategoryResponse(Category category){
        this.categoryId = category.getId();
        this.categoryName = category.getCategoryName();
        this.parentId = category.getParent().getId();
    }
}
