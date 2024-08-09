package com.example.mssaembackendv2.domain.chatroom;

import com.example.mssaembackendv2.domain.worryboard.WorryBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findChatRoomByWorryBoard(WorryBoard worryBoard);
}
