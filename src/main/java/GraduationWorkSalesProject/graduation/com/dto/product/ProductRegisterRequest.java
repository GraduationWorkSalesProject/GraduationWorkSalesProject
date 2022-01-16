package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import GraduationWorkSalesProject.graduation.com.vo.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel(value = "상품 등록 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRegisterRequest {

    @ApiModelProperty(value = "상품명", example = "상품1", required = true)
    @NotBlank(message = "상품명은 필수입니다")
    private String productName;

    @ApiModelProperty(value = "상품가격", example = "10000", required = true)
    @Range(min = 1, message= "상품 가격은 적어도 1 이상의 유효한 값이어야 합니다")
    private int productPrice;

    //length limit of product info?
    @ApiModelProperty(value = "상품설명", example = "상품1입니다", required = true)
    @NotBlank(message = "상품 설명은 필수입니다")
    private String productInforamtion;

    @ApiModelProperty(value = "상품 카테고리", example = "1", required = true)
    @Range(min = 1, message= "카테고리 번호는 유효한 값이어야 합니다")
    private Long categoryId;

    @ApiModelProperty(value = "상품 해시태그")
    private List<String> hashtags;

    @ApiModelProperty(value = "상품 배송기간")
    private int productDeliveryTerm;

    @ApiModelProperty(value = "상품 배송비")
    private int productDeliveryPrice;

    @ApiModelProperty(value = "상품 대표이미지")
    private MultipartFile productRepImage;

    @ApiModelProperty(value = "상품 사진1")
    private MultipartFile productImage1;

    @ApiModelProperty(value = "상품 사진2")
    private MultipartFile productImage2;

    @ApiModelProperty(value = "상품 사진3")
    private MultipartFile productImage3;

    @ApiModelProperty(value = "상품 사진4")
    private MultipartFile productImage4;

    public Product convert(Image productRepImage, Member member) {
        return Product.builder()
                .member(member)
                .name(getProductName())
                .price(getProductPrice())
                .information(getProductInforamtion())
                .term(getProductDeliveryTerm())
                .deliveryPrice(getProductDeliveryPrice())
                .representationImage(productRepImage)
                .build();
    }

    public ProductRegisterRequest(String productName, int productPrice, String productInforamtion, Long categoryId, List<String> hashtags, int productDeliveryTerm, int productDeliveryPrice,MultipartFile productRepImage, MultipartFile productImage1, MultipartFile productImage2, MultipartFile productImage3, MultipartFile productImage4) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInforamtion = productInforamtion;
        this.categoryId = categoryId;
        this.hashtags = hashtags;
        this.productDeliveryTerm = productDeliveryTerm;
        this.productDeliveryPrice = productDeliveryPrice;
        this.productRepImage = productRepImage;
        this.productImage1 = productImage1;
        this.productImage2 = productImage2;
        this.productImage3 = productImage3;
        this.productImage4 = productImage4;

    }

}
