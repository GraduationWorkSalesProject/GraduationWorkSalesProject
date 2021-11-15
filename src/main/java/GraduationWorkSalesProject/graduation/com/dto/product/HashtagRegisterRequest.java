package GraduationWorkSalesProject.graduation.com.dto.product;


import GraduationWorkSalesProject.graduation.com.entity.product.Hashtag;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "해시태그 등록 모델")
@Getter
@Setter
@NoArgsConstructor
public class HashtagRegisterRequest {

    @NotEmpty
    private String hashtagName;

    public Hashtag covert(){
        return Hashtag
                .builder()
                .hashtagName(getHashtagName())
                .build();
    }
}
