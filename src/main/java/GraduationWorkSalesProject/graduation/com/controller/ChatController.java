package GraduationWorkSalesProject.graduation.com.controller;

import GraduationWorkSalesProject.graduation.com.dto.chat.*;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.*;

@Api(tags = "채팅 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    /**
     * 상품 정보 -> <b>번개톡</b> 클릭 시, <b>채팅방 생성</b> + <b>해당 채팅방으로 이동</b><br>
     * <li>이미 채팅방이 존재하면, 생성 x</li>
     * <li>해당 채팅방으로 이동하기 위해, 채팅방 번호 응답</li>
     *
     * @return 채팅방 번호, 채팅방 생성 여부
     */
    @ApiOperation(value = "채팅방 생성")
    @PostMapping("/rooms")
    public ResponseEntity<ResultResponse> createChatRoom(@Validated @RequestBody CreateChatRoomRequest request) {
        final Long roomId = chatService.createRoom(request.getSellerId(), request.getProductId());
        final CreateChatRoomResponse response = new CreateChatRoomResponse(roomId);

        return ResponseEntity.ok(ResultResponse.of(null, response));
    }

    @ApiOperation(value = "채팅방 목록 조회")
    @GetMapping("/rooms")
    public ResponseEntity<ResultResponse> chatRoomList() {
        final List<ChatRoomDTO> roomList = chatService.findJoinRooms();
        final ChatRoomsResponse response = new ChatRoomsResponse(roomList);

        return ResponseEntity.ok(ResultResponse.of(null, response));
    }

    @MessageMapping("/chat/send")
    public void sendMessage(@Validated @RequestBody MessageRequest request) {
        chatService.saveMessage(request.getRoomId(), request.getContent(), request.getType());
        messagingTemplate.convertAndSend("/sub/chat/room/" + request.getRoomId(), request);
    }
}
