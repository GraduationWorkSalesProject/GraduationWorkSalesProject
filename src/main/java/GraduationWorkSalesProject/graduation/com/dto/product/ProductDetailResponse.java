package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.product.Hashtag;
import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;
import GraduationWorkSalesProject.graduation.com.vo.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "상세 상품 모델")
public class ProductDetailResponse {

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

    @ApiModelProperty(value = "상품 배송 기간")
    private int productDeliveryTerm;

    @ApiModelProperty(value = "상품 배송비")
    private int productDeliveryPrice;

    @ApiModelProperty(value = "판매자 아이디")
    private Long sellerId;

    @ApiModelProperty(value = "판매자 정보")
    private String sellerInformation;

    @ApiModelProperty(value = "판매자명")
    private String sellerName;

    @ApiModelProperty(value = "상품 대표이미지")
    private Image representationImage;

    @ApiModelProperty(value = "상품 이미지 목록")
    private List<Image> productImageList;

    @ApiModelProperty(value = "상품 해시태그")
    private List<Hashtag> hashtagList;

    @ApiModelProperty(value = "로그인 한 회원의 판매자 팔로우 여부")
    private int follow;


    public ProductDetailResponse(Product product, Seller seller, List<Image> productImageList, List<Hashtag> hashtagList) {
        this.id = product.getId();
        this.productName = product.getName();
        this.productPrice = product.getPrice();
        this.productInformation = product.getInformation();
        this.productRegisterDate = product.getRegisterDate();
        this.productUpdateDate = product.getUpdateDate();
        this.productRating = product.getRating();
        this.productDeliveryTerm = product.getTerm();
        this.productDeliveryPrice = product.getDeliveryPrice();
        this.sellerId = seller.getId();
        this.sellerInformation = seller.getSellerInformation();
        this.sellerName = seller.getSellerName();
        this.representationImage = product.getRepresentationImage();
        this.productImageList = productImageList;
        this.hashtagList = hashtagList;
    }
}
