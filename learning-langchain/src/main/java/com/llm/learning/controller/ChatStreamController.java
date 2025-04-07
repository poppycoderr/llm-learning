package com.llm.learning.controller;

import com.llm.learning.service.ChatAssistant;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author Elijah Du
 * @date 2025/4/1
 */
@Slf4j
@RequestMapping("/chat/stream")
@RequiredArgsConstructor
@RestController
public class ChatStreamController {

    private final StreamingChatLanguageModel chatModel;
    private final ChatAssistant chatAssistant;

    @GetMapping("/v1")
    public void chatV1(String message) {
        chatModel.chat(message, new StreamingChatResponseHandler() {

            @Override
            public void onPartialResponse(String partialResponse) {
                log.warn("onPartialResponse: {}", partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                log.warn("onCompleteResponse: {}", completeResponse);
            }

            @Override
            public void onError(Throwable error) {
                log.error("onError", error);
            }
        });
    }

    @GetMapping("/v2")
    public void chatV2(String message) {
        chatAssistant.streamChatV2(message)
                .onPartialResponse(System.out::println)
                .onRetrieved(System.out::println)
                .onToolExecuted(System.out::println)
                .onCompleteResponse(System.out::println)
                .onError(Throwable::printStackTrace)
                .start();
    }

    @GetMapping(value = "/v3", produces = MediaType.TEXT_EVENT_STREAM_VALUE + ";charset=UTF-8")
    public Flux<String> chatV3(String message) {
        return chatAssistant.streamChatV3(message);
    }
}
