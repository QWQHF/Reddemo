package com.example.demo.controller;

import com.example.demo.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    private final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> payload) {
        String sessionId = payload.get("sessionId"); // 用户唯一标识，比如 UUID 或用户名
        String message = payload.get("message");

        String reply = chatService.chat(sessionId, message);
        return Map.of("reply", reply);
    }

    @PostMapping("/clear")
    public void clearSession(@RequestBody Map<String, String> payload) {
        chatService.clearSession(payload.get("sessionId"));
    }
}