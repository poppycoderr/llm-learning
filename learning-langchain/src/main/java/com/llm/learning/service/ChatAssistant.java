package com.llm.learning.service;

import com.llm.learning.structured.Person;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

/**
 * @author Elijah Du
 * @date 2025/3/17
 */
public interface ChatAssistant {

    // @SystemMessage("以法语格式输出")
    String chat(String message);

    String chat(@MemoryId String memoryId, @UserMessage String message);

    Person chatWithStructured(String message);

    TokenStream streamChatV2(String message);

    Flux<String> streamChatV3(String message);
}
