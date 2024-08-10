package com.example.mssaembackendv2.domain.dynamoChatConnections;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Getter
@Setter
@DynamoDbBean
public class ChatConnection {

    private String chatRoomId;
    private String connectionId;
    private String memberId;

    @DynamoDbPartitionKey
    public String getChatRoomId() {
        return chatRoomId;
    }

    @DynamoDbSortKey
    public String getConnectionId() {
        return connectionId;
    }

    public String getMemberId() {
        return memberId;
    }

}
