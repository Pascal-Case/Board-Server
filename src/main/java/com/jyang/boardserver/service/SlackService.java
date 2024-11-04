package com.jyang.boardserver.service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlackService {
    @Value("${slack.token}")
    String slackToken;

    public void sendSlackMessage(String message, String channel) {

        try {
            MethodsClient methodsClient = Slack.getInstance().methods(slackToken);
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA);
            String formattedDate = dateFormat.format(now);

            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel("#monitoring")
                    .text(formattedDate + "\n" + message)
                    .build();
            ChatPostMessageResponse response = methodsClient.chatPostMessage(request);

            if (!response.isOk()) {
                log.error("슬랙 메시지 전송 실패 {}", response);
                throw new RuntimeException("Error Sending message to Slack " + response.getError());
            }

            log.info(channel);
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
        }
    }
}
