package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@ApiModel(value = "개별 상품 모델")
@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {

    @ApiModelProperty(value = "상품 아이디")
    private Long id;

    @ApiModelProperty(value = "상품명")
    private String productName;

    @ApiModelProperty(value = "카테고리 아이디")
    private Long categoryId;

    @ApiModelProperty(value = "카테고리명")
    private String categoryName;

    @ApiModelProperty(value = "상품 가격")
    private int productPrice;

    @ApiModelProperty(value = "상품 정보")
    private String productInformation;

    @ApiModelProperty(value = "상품 등록 날짜")
    private Timestamp productRegisterDate;

    @ApiModelProperty(value = "상품 마지막 수정 날짜")
    private Timestamp productUpdateDate;

    @ApiModelProperty(value = "상품 점수")
    private int productRating;

    @ApiModelProperty(value = "상품 배송기간")
    private int productDeliveryTerm;

    @ApiModelProperty(value = "상품 배송비")
    private int productDeliveryPrice;


    public ProductResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getName();
        this.productPrice = product.getPrice();
        this.productInformation = product.getInformation();
        this.productRegisterDate = product.getRegisterDate();
        this.productUpdateDate = product.getUpdateDate();
        this.productRating = product.getRating();
        this.productDeliveryTerm = product.getTerm();
        this.productDeliveryPrice = product.getDeliveryPrice();
    }

}
