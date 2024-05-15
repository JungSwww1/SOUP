package io.ssafy.soupapi.domain.chat.dao;

import io.ssafy.soupapi.domain.chat.dto.RChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.connection.zset.DefaultTuple;
import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class RChatRepository {

    private static final String CHATROOM_HASH = "chatroom:";
    private static final String CHATROOM_HASH_PATTERN = "chatroom:*";

    private final RedisTemplate<String, RChatMessage> redisTemplateChatMessage;

    public void saveMessageToRedis(String chatroomId, RChatMessage rChatMessage, long sentAt) {
        redisTemplateChatMessage.opsForZSet().add(CHATROOM_HASH + chatroomId, rChatMessage, sentAt);
    }

    public void saveMessagesToRedis(String chatroomId, List<RChatMessage> rChatMessages, List<Long> scores) {
        RedisSerializer keySerializer = redisTemplateChatMessage.getKeySerializer();
        RedisSerializer valueSerializer = redisTemplateChatMessage.getValueSerializer();

        redisTemplateChatMessage.executePipelined( (RedisCallback<Object>)connection -> {
            if (scores == null || scores.isEmpty()) {
                Double score = Long.valueOf(System.currentTimeMillis()).doubleValue(); // 현재 시간
                rChatMessages.forEach(rChatMessage -> {
                    connection.zAdd(
                            keySerializer.serialize(CHATROOM_HASH + chatroomId),
                            score,
                            valueSerializer.serialize(rChatMessage)
                    );
                });
            }
            return null;
        });

    }

    public List<RChatMessage> getMessageByIndex(String chatroomId, int startIndex, int endIndex) {
        Set<RChatMessage> RChatMessageSet = redisTemplateChatMessage.opsForZSet().range(CHATROOM_HASH + chatroomId, startIndex, endIndex);
        return new ArrayList<>(RChatMessageSet);
    }

    public List<RChatMessage> getNMessagesBefore(String chatroomId, Long before, long offset, long size) {
//        log.info("[RChatMessage] redis에서 score {} 이전의 메시지들을 조회합니다.", before);
        Set<RChatMessage> RChatMessageSet
                = redisTemplateChatMessage.opsForZSet().reverseRangeByScore(CHATROOM_HASH + chatroomId, 0, before, offset, size);
        return new ArrayList<>(RChatMessageSet);
    }

    public void deleteMessageFromRedis(long minScore, long maxScore) {
        Set<String> keys = redisTemplateChatMessage.keys(CHATROOM_HASH_PATTERN);
        for (String key: keys) {
            redisTemplateChatMessage.opsForZSet().removeRangeByScore(key, minScore, maxScore);
        }
    }

}