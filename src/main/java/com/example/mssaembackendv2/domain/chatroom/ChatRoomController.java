package com.example.mssaembackendv2.domain.chatroom;

import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.global.config.security.auth.CurrentMember;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 생성
     */
    @PostMapping("/chatroom")
    public ResponseEntity<Long> createChatRoom(@CurrentMember Member member, @RequestParam Long worryBoardId){
        return ResponseEntity.ok(chatRoomService.createChatRoom(member, worryBoardId));
    }
}
