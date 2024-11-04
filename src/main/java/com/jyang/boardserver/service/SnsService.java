package com.jyang.boardserver.service;

import com.jyang.boardserver.config.AwsConfigure;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Service
@Getter
@RequiredArgsConstructor
public class SnsService {

    private final AwsConfigure awsConfigure;

    public AwsCredentialsProvider credentialsProvider(String accessKey, String secretKey) {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        return () -> awsBasicCredentials;
    }

    @Bean
    public SnsClient getSnsClient() {
        return SnsClient.builder()
                .region(Region.of(awsConfigure.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(awsConfigure.getAccessKey(), awsConfigure.getSecretKey())
                        )
                )
                .build();
    }
}
