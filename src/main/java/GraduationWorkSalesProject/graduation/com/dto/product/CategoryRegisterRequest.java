package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.product.Category;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "카테고리 등록 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryRegisterRequest {

    @NotEmpty
    private String category_name;

    private Long category_parent_id;

    public Category convert(){
        return Category.builder().categoryName(category_name).build();
    }
}
