package com.hrpolicy.controller;

import com.hrpolicy.rag.DataLoader;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MyChatController {

    private final ChatClient chatClient;

    private final DataLoader dataLoader;

    public MyChatController(ChatClient chatClient, DataLoader dataLoader) {
        this.chatClient = chatClient;
        this.dataLoader = dataLoader;
    }

    @GetMapping("/chat")
    public Map<String,String> letsChat(@RequestParam String msg){
        String answer = chatClient.prompt()
                .user(msg)
                .call()
                .content();

        return Map.of("answer",answer);

    }

    //Load pdf into Qdrant
    @PostMapping("/save")
    public Map<String,Object> storeDat(){
        int totalChunks = dataLoader.loadDataInVectorDb();
        return Map.of("message","Pdf data stored successfully",
                "totalChunks:",totalChunks);
    }
}
