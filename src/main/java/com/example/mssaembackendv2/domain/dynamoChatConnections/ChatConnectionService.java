package com.example.mssaembackendv2.domain.dynamoChatConnections;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;

@Service
public class ChatConnectionService {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<ChatConnection> chatConnectionTable;

    public ChatConnectionService(DynamoDbClient dynamoDbClient) {
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.chatConnectionTable = enhancedClient.table("ChatConnections", TableSchema.fromBean(ChatConnection.class));
    }

    public List<ChatConnection> getConnectionsByChatRoomId(String chatRoomId) {
        return chatConnectionTable.query(r -> r.queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(chatRoomId))))
                .items()
                .stream()
                .toList();
    }

    public ChatConnection getConnectionByConnectionId(String connectionId) {
        return chatConnectionTable.getItem(r -> r.key(k -> k.partitionValue(connectionId)));
    }

}
