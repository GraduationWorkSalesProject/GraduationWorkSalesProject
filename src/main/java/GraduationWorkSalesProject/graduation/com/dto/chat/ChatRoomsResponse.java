package GraduationWorkSalesProject.graduation.com.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomsResponse {

    private List<ChatRoomDTO> roomList = new ArrayList<>();
}
