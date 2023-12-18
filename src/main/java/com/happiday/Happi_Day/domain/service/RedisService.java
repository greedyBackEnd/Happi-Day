package com.happiday.Happi_Day.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {
    private final long clientViewCountExpireDurationOfSecond = 86400L;
    private final RedisTemplate<String, String> redisTemplate;

    public boolean isFirstIpRequest(String clientAddress, Long eventId) {
        String key = generateKey(clientAddress, eventId);
        log.info("클라이언트 주소 + eventId : {}", key);
        if (redisTemplate.hasKey(key)) {
            log.info("이미 조회");
            return false;
        }
        return true;
    }

    public void clientRequest(String clientAddress, Long eventId) {
        String key = generateKey(clientAddress, eventId);
        log.info("클라이언트 주소 + eventId : {}", key);

        redisTemplate.opsForValue().set(key, "processed");
        redisTemplate.expire(key, clientViewCountExpireDurationOfSecond, TimeUnit.SECONDS);
    }

    // key 형식 : 'client Address + eventId' ->  '0:0:0:0:0:0:0:1 + 2'
    private String generateKey(String clientAddress, Long eventId) {
        return clientAddress + " + " + eventId;
    }
}