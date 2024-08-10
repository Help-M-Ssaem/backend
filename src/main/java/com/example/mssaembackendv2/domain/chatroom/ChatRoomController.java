package com.example.mssaembackendv2.domain.chatroom;

import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.global.config.security.auth.CurrentMember;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatRoomController {

//    @PostMapping("/chatroom")
//    public ResponseEntity<Long> createChatRoom(@CurrentMember Member member, @RequestParam Long worryBoardId){
//
//
//    }
}
