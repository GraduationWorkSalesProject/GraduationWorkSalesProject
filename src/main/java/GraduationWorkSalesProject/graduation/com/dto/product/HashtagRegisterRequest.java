package GraduationWorkSalesProject.graduation.com.dto.product;


import GraduationWorkSalesProject.graduation.com.entity.product.Hashtag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "해시태그 등록 모델")
@Getter
@Setter
@NoArgsConstructor
public class HashtagRegisterRequest {

    @ApiModelProperty(value = "해시태그명", example = "해시태그1", required = true)
    @NotEmpty(message="해시태그명은 필수입니다다")
   private String hashtagName;

    public Hashtag covert(){
        return Hashtag
                .builder()
                .hashtagName(getHashtagName())
                .build();
    }
}
