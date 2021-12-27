package GraduationWorkSalesProject.graduation.com.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel(value = "상품 좋아요 수 조회 모델")
public class ProductLikeNumResponse {

    @ApiModelProperty(value = "좋아요 누른 사람 수")
    private Long like_num;

    public ProductLikeNumResponse(Long like_num){
        this.like_num = like_num;
    }

}
