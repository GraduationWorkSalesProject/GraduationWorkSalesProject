package GraduationWorkSalesProject.graduation.com.entity.chat;

import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Column(name = "message_content")
    private String content;

    @CreatedDate
    @Column(name = "message_date")
    private LocalDateTime createdDate;

    @Column(name = "message_flag")
    private boolean readFlag;

    @Column(name = "message_type")
    private MessageType type;

    @Builder
    public Message(Member member, ChatRoom chatRoom, String content, MessageType type) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.content = content;
        this.readFlag = false;
        this.type = type;
    }

    public enum MessageType {
        TEXT
    }
}
