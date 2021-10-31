package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRegisterRequest {


    @NotEmpty(message = "상품명은 필수입니다")
    private String productName;

    @NotEmpty(message = "상품 가격은 필수입니다")
    private int productPrice;

    //length limit of product info?
    @NotEmpty(message = "상품 설명은 필수입니다")
    private String productInforamtion;

    @NotEmpty(message = "상품은 카테고리에 속해야 합니다")
    private Long category_id;

    public Product convert(){
        return Product.builder()
                .productName(getProductName())
                .productPrice(getProductPrice())
                .productInformation(getProductInforamtion())
                .build();
    }
}
