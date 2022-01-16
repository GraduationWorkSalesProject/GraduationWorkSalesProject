package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.vo.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApiModel(value = "상품이 많은 해시태그 조회 모델")
@Getter
@AllArgsConstructor
public class HashtagProductResponse {

    @ApiModelProperty(value = "해시태그 아이디")
    private Long id;

    @ApiModelProperty(value = "해시태그 이름")
    private String hashtagName;

    @ApiModelProperty(value = "해시태그 내 랜덤 상품 이미지")
    private Image hashtagProductImage;


}
