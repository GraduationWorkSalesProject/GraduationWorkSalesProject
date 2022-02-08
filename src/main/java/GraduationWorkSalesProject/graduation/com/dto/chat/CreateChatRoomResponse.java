package GraduationWorkSalesProject.graduation.com.dto.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "채팅방 생성 응답 데이터 모델")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomResponse {

    @ApiModelProperty(value = "채팅방 PK", example = "1")
    private Long roomId;
}
