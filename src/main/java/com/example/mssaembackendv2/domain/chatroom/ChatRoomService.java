package com.example.mssaembackendv2.domain.chatroom;

import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.domain.worryboard.WorryBoard;
import com.example.mssaembackendv2.domain.worryboard.WorryBoardRepository;
import com.example.mssaembackendv2.domain.worryboard.WorryBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final WorryBoardRepository worryBoardRepository;

    public Long createChatRoom(Member member, Long worryBoardId) {
        WorryBoard worryBoard = worryBoardRepository.findById(worryBoardId).orElseThrow();

        ChatRoom chatRoom = new ChatRoom(worryBoard.getTitle(), worryBoard);
        chatRoomRepository.save(chatRoom);

        return chatRoom.getId();
    }
}
