package GraduationWorkSalesProject.graduation.com.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value = "최근 본 상품 아이디 리스트 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecentViewRequest {
    @ApiModelProperty(value = "최근 본 상품 리스트")
    List<Long> productId;
}
