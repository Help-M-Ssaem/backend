package com.example.mssaembackendv2.global.config.dynamoDB;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        return DynamoDbClient.builder()
                .region(Region.AP_NORTHEAST_2)  // 필요한 지역으로 설정
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))  // 자격 증명 직접 설정
                .build();
    }
}
