package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.dto.chat.ChatRoomDTO;
import GraduationWorkSalesProject.graduation.com.entity.chat.JoinRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JoinRoomRepository extends JpaRepository<JoinRoom, Long>/*, JoinRoomRepositoryCustom*/ {

    @Query("select new GraduationWorkSalesProject.graduation.com.dto.chat" +
            ".ChatRoomDTO(j.chatRoom.id, j.id, j.chatRoom.seller.username) " +
            "from JoinRoom j " +
            "where j.member.id = :memberId")
    List<ChatRoomDTO> findAllByMemberId(@Param("memberId") Long memberId);
}
