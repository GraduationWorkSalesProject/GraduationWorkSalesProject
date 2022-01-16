package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.product.Hashtag;
import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import GraduationWorkSalesProject.graduation.com.vo.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private Date productRegisterDate;

    @ApiModelProperty(value = "상품 마지막 수정 날짜")
    private Date productUpdateDate;

    @ApiModelProperty(value = "상품 점수")
    private int productRating;

    @ApiModelProperty(value = "상품 대표이미지")
    private Image representationImage;

    @ApiModelProperty(value = "상품 해시태그")
    private List<Hashtag> hashtagList;

    @ApiModelProperty(value = "판매자 이름")
    private String sellerName;

    @ApiModelProperty(value = "좋아요 여부")
    private Long like;


    public ProductResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getName();
        this.productPrice = product.getPrice();
        this.productInformation = product.getInformation();
        this.productRegisterDate = product.getRegisterDate();
        this.productUpdateDate = product.getUpdateDate();
        this.productRating = product.getRating();
        this.representationImage = product.getRepresentationImage();
    }

    public ProductResponse(Long productId, Long categoryId, String categoryName, String productName, int productPrice,
                           String productInformation, Date productRegisterDate, Date productUpdateDate,
                           int productRating, Image representationImage, String sellerName) {
        this.id = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productPrice = productPrice;
        this.productInformation = productInformation;
        this.productRegisterDate = productRegisterDate;
        this.productUpdateDate = productUpdateDate;
        this.productRating = productRating;
        this.representationImage = representationImage;
        this.sellerName = sellerName;
    }

}
