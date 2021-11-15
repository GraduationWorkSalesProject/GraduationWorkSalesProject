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

    @NotEmpty(message = "카테고리이름을 입력해주세요")
    private String categoryName;

    private Long categoryParentId;

    public Category convert(){
        return Category.builder().categoryName(categoryName).build();
    }
}
