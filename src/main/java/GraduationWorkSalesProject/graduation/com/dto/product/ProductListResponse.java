package GraduationWorkSalesProject.graduation.com.dto.product;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel(value = "상품 리스트 모델")
public class ProductListResponse {

    @ApiModelProperty(value = "상품 리스트")
    private List<ProductResponse> productList;
}
