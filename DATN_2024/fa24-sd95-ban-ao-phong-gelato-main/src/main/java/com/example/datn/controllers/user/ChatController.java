package com.example.datn.controllers.user;

import com.example.datn.dto.ChatBot.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        String responseText;

        if (message.getText().contains("xin chào")) {
            responseText = "Xin chào! Tôi có thể giúp gì cho bạn?";
        } else if (message.getText().contains("giá sản phẩm")) {
            responseText = "Sản phẩm của chúng tôi có giá từ 500k đến 2 triệu đồng.";
        } else {
            responseText = "Xin lỗi, tôi không hiểu yêu cầu của bạn.";
        }

        ChatMessage response = new ChatMessage();
        response.setFrom("Bot");
        response.setText(responseText);

        return response;
    }

}

