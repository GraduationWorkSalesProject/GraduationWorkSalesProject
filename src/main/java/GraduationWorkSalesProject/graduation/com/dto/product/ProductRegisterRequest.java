package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel(value = "상품 등록 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRegisterRequest {

    @ApiModelProperty(value = "상품명", example = "상품1", required = true)
    @NotEmpty(message = "상품명은 필수입니다")
    private String productName;

    @ApiModelProperty(value = "상품가격", example = "10000", required = true)
    @NotEmpty(message = "상품 가격은 필수입니다")
    private int productPrice;

    //length limit of product info?
    @ApiModelProperty(value = "상품설명", example = "상품1입니다", required = true)
    @NotEmpty(message = "상품 설명은 필수입니다")
    private String productInforamtion;

    @ApiModelProperty(value = "상품 카테고리", example = "1", required = true)
    @NotEmpty(message = "상품은 카테고리에 속해야 합니다")
    private Long categoryId;

    private List<String> hashtags;

    public Product convert(){
        return Product.builder()
                .name(getProductName())
                .price(getProductPrice())
                .information(getProductInforamtion())
                .build();
    }
}
