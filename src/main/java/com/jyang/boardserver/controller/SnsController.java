package com.jyang.boardserver.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyang.boardserver.service.SlackService;
import com.jyang.boardserver.service.SnsService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsResponse;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SnsController {
    private final ObjectMapper objectMapper;
    private final SnsService snsService;
    private final SlackService slackService;

    @PostMapping("/create-topic")
    public ResponseEntity<String> createTopic(
            @RequestParam final String topicName
    ) {
        final CreateTopicRequest topicRequest = CreateTopicRequest.builder()
                .name(topicName)
                .build();

        SnsClient snsClient = snsService.getSnsClient();

        final CreateTopicResponse topicResponse = snsClient.createTopic(topicRequest);

        if (!topicResponse.sdkHttpResponse().isSuccessful()) {
            log.error(topicResponse.toString());
            throw GetResponseException(topicResponse);
        }

        log.info("topic name = " + topicResponse.topicArn());
        snsClient.close();
        return new ResponseEntity<>("TOPIC CREATING SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(
            @RequestParam final String endpoint,
            @RequestParam final String topicArn
    ) {
        final SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("https")
                .topicArn(topicArn)
                .endpoint(endpoint)
                .build();
        SnsClient snsClient = snsService.getSnsClient();
        final SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);

        if (!subscribeResponse.sdkHttpResponse().isSuccessful()) {
            throw GetResponseException(subscribeResponse);
        }

        log.info("topicARN to subscribe = " + subscribeResponse.subscriptionArn());
        snsClient.close();
        return new ResponseEntity<>("TOPIC SUBSCRIBE SUCCESS", HttpStatus.OK);

    }

    @PostMapping("publish")
    public String publish(
            @RequestParam final String topicArn,
            @RequestBody Map<String, Object> message) throws JsonProcessingException {

        String messageJson = objectMapper.writeValueAsString(message);
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message(messageJson)
                .build();
        SnsClient snsClient = snsService.getSnsClient();
        final PublishResponse publishResponse = snsClient.publish(publishRequest);

        log.info("message : " + publishResponse.sdkHttpResponse().statusCode());
        snsClient.close();

        return publishResponse.messageId();
    }

    @GetMapping("/slack/{channel}")
    public void error(@PathVariable(name = "channel") String channel) {
        slackService.sendSlackMessage("test message", channel);
    }

    private ResponseStatusException GetResponseException(SnsResponse snsResponse) {
        return new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, snsResponse.sdkHttpResponse().statusText().get()
        );
    }

}
