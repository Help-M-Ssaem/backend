package com.example.mssaembackendv2.domain.chatroom;


import com.example.mssaembackendv2.domain.worryboard.WorryBoard;
import com.example.mssaembackendv2.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private WorryBoard worryBoard;

    public ChatRoom(String title, WorryBoard worryBoard) {
        this.title = title;
        this.worryBoard = worryBoard;
    }

    public void setChatRoomTitle(String title){
        this.title = title;
    }
}
