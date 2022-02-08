package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.dto.chat.ChatRoomDTO;

import java.util.List;

public interface JoinRoomRepositoryCustom {
    List<ChatRoomDTO> findAllByMemberId(Long memberId);
}
