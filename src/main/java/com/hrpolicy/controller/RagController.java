package com.hrpolicy.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rag")
public class RagController {
    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    private final Resource promptFile;


    public RagController(VectorStore vectorStore, ChatClient chatClient,
                         @Value("classpath:/ai-prompt/rag-prompt.st")Resource promptFile) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
        this.promptFile = promptFile;
    }

    @GetMapping("/chat")
    public Map<String,String> letsChat(@RequestParam String msg){

        //Step 1: Create chat request
        SearchRequest searchRequest = SearchRequest.builder()
                .query(msg)
                .topK(5)
                .similarityThreshold(0.5)
                .build();

        //Search relevant doc in Qdrant
        List<Document> documentList = vectorStore.similaritySearch(searchRequest);

        //Check weather relevant info exists
        if(documentList==null || documentList.isEmpty()){
                return Map.of("answer","I don't know based on the uploaded document");

        }

     //Convert retrieved document into a single string
        String data = documentList.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        //Send retrieved info to OpenAI
        String answer = chatClient.prompt()
                .system(system -> system
                        .text(promptFile)
                        .param("document", data))
                .user(msg)
                .call()
                .content();

        return Map.of("answer",answer);
    }
}
