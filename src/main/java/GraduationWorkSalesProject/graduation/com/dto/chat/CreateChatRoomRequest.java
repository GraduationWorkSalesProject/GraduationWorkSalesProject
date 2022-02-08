package GraduationWorkSalesProject.graduation.com.dto.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "채팅방 생성 요청 데이터 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateChatRoomRequest {

    @ApiModelProperty(value = "판매자 PK", example = "2", required = true)
    private Long sellerId;
    @ApiModelProperty(value = "상품 PK", example = "1", required = true)
    private Long productId;
}
