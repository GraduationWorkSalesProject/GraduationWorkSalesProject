package GraduationWorkSalesProject.graduation.com.dto.product;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductListResponse {
    private List<ProductResponse> productList;
}
