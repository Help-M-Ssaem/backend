package com.example.mssaembackendv2.domain.dynamoChatConnections;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatConnectionService chatConnectionService;

    @GetMapping ("/chatconnection")
    public List<ChatConnection> chatConnections(@RequestParam(required = false) String chatRoomId) {
        return chatConnectionService.getConnectionsByChatRoomId(chatRoomId);
    }
}
