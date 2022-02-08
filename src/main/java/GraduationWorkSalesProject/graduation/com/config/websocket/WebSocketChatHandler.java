package GraduationWorkSalesProject.graduation.com.config.websocket;

import GraduationWorkSalesProject.graduation.com.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ChatService chatService;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        final String payload = message.getPayload();

//        MessageDTO messageDTO = objectMapper.readValue(payload, MessageDTO.class);
//        JoinRoom room = chatService.findRoomById(messageDTO.getRoomId());
//        room.handleActions(session, messageDTO, chatService);
    }
}
