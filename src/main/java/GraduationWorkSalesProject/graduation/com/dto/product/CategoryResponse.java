package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.product.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private Long parentId;

    public CategoryResponse(Category category){
        this.categoryId = category.getId();
        this.categoryName = category.getCategoryName();
        this.parentId = category.getParent().getId();
    }
}
