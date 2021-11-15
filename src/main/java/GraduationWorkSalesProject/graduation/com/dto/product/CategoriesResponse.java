package GraduationWorkSalesProject.graduation.com.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoriesResponse {
    private List<CategoryResponse> categoryList;
}
