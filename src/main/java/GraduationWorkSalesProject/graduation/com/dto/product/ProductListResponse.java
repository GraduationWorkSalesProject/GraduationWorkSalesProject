package GraduationWorkSalesProject.graduation.com.dto.product;


import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductListResponse {
    List<Product> productList;
}
