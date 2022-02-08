package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.dto.chat.ChatRoomDTO;
import GraduationWorkSalesProject.graduation.com.entity.chat.QChatRoom;
import GraduationWorkSalesProject.graduation.com.entity.chat.QJoinRoom;
import GraduationWorkSalesProject.graduation.com.entity.chat.QMessage;
import GraduationWorkSalesProject.graduation.com.entity.member.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static GraduationWorkSalesProject.graduation.com.entity.chat.QChatRoom.chatRoom;
import static GraduationWorkSalesProject.graduation.com.entity.chat.QJoinRoom.joinRoom;
import static GraduationWorkSalesProject.graduation.com.entity.chat.QMessage.message;
import static GraduationWorkSalesProject.graduation.com.entity.member.QMember.member;

@RequiredArgsConstructor
public class JoinRoomRepositoryCustomImpl implements JoinRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoomDTO> findAllByMemberId(Long memberId) {
        System.out.println("memberId = " + memberId);
        return queryFactory
                .select(
                        Projections.fields(
                                ChatRoomDTO.class,
                                joinRoom.chatRoom.id,
//                                joinRoom.id,
                                joinRoom.chatRoom.seller.username
                                /*JPAExpressions
                                        .select(message.createdDate)
                                        .from(message)
                                        .where(message.chatRoom.id.eq(joinRoom.chatRoom.id))
                                        .orderBy(message.id.desc())
                                        .limit(1),
                                JPAExpressions
                                        .select(message.content)
                                        .from(message)
                                        .where(message.chatRoom.id.eq(joinRoom.chatRoom.id))
                                        .orderBy(message.id.desc())
                                        .limit(1)*/
                        )
                )
                .from(joinRoom)
//                .where(joinRoom.member.id.eq(memberId))
                .fetch();
    }
}
