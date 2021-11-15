package GraduationWorkSalesProject.graduation.com.dto.product;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductLikeNumResponse {
    private Long like_num;

    public ProductLikeNumResponse(Long like_num){
        this.like_num = like_num;
    }

}
