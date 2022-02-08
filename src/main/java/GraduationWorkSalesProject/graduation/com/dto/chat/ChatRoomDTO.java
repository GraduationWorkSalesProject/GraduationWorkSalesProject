package GraduationWorkSalesProject.graduation.com.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {

    private Long chatRoomId;
    private Long joinRoomId;
    private String username;
    /*private LocalDateTime recentMessageDate;
    private String recentMessage;*/
}
