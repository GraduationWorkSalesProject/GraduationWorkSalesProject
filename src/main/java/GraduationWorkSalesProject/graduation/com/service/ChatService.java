package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.dto.chat.ChatRoomDTO;
import GraduationWorkSalesProject.graduation.com.entity.chat.ChatRoom;
import GraduationWorkSalesProject.graduation.com.entity.chat.JoinRoom;
import GraduationWorkSalesProject.graduation.com.entity.chat.Message;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.exception.MemberNotFoundException;
import GraduationWorkSalesProject.graduation.com.repository.ChatRoomRepository;
import GraduationWorkSalesProject.graduation.com.repository.JoinRoomRepository;
import GraduationWorkSalesProject.graduation.com.repository.MemberRepository;
import GraduationWorkSalesProject.graduation.com.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final JoinRoomRepository joinRoomRepository;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveMessage(Long roomId, String content, Message.MessageType type) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member member = memberRepository.findByUsername(username).orElseThrow(MemberNotFoundException::new);
        final ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(); // TODO: 예외처리
        messageRepository.save(new Message(member, chatRoom, content, type));
    }

    @Transactional
    public Long createRoom(Long sellerId, Long productId) {
        // TODO: Exception 리팩토링 -> 하나의 예외로 메시지만 다르게 생성하도록 ex)userid, username 예외
        //  상품 존재하는지 확인 -> 예외 처리
        final String buyerName = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member seller = memberRepository.findById(sellerId).orElseThrow(MemberNotFoundException::new);
        final Member buyer = memberRepository.findByUsername(buyerName).orElseThrow(MemberNotFoundException::new);
        final Optional<ChatRoom> findChatRoom = chatRoomRepository.findByBuyerIdAndSellerId(buyer.getId(), sellerId);

        if (findChatRoom.isEmpty()) {
            final ChatRoom chatRoom = chatRoomRepository.save(new ChatRoom(seller, buyer));
            joinRoomRepository.save(new JoinRoom(buyer, chatRoom));
            return chatRoom.getId();
        }

        return findChatRoom.get().getId();
    }

    public List<ChatRoomDTO> findJoinRooms() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member member = memberRepository.findByUsername(username).orElseThrow(MemberNotFoundException::new);
        return joinRoomRepository.findAllByMemberId(member.getId()); // TODO: 테스트 필요
    }
}
