package io.ssafy.soupapi.domain.chat.dto.request;

import io.ssafy.soupapi.domain.chat.dto.ChatSender;
import io.ssafy.soupapi.domain.chat.entity.RChatMessage;
import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;

import java.time.Instant;
import java.util.List;

// 웹소켓 stomp 로 채팅을 할 때 publisher가 보내오는 메시지
public record ChatMessageReq(
        ChatSender sender,
        String message,
        List<String> mentionedMemberIds // 누군가를 태그했을 때 태그 당한 memberId
) {

    public RChatMessage toRChatMessage(String chatMessageId, Instant sentAt) {
        return RChatMessage.builder()
                .chatMessageId(chatMessageId)
                .senderId(sender.getMemberId()).message(message).sentAt(sentAt)
                .build();
    }

    public ChatMessage toMChatMessage(String chatMessageId, Instant sentAt) {
        return ChatMessage.builder()
                .id(chatMessageId)
                .senderId(sender.getMemberId()).content(message).timestamp(sentAt)
                .build();
    }

}
