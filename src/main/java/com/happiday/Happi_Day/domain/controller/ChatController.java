package com.happiday.Happi_Day.domain.controller;

import com.happiday.Happi_Day.domain.entity.chat.dto.ChatMessageDto;
import com.happiday.Happi_Day.domain.service.ChatService;
import com.happiday.Happi_Day.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{roomId}/send")
    public ResponseEntity<ChatMessageDto> sendMessage(
            @PathVariable("roomId") Long roomId,
            @RequestBody ChatMessageDto chatMessageDto
    ) {
        String username = SecurityUtils.getCurrentUsername();
        return new ResponseEntity<>(chatService.sendMessage(username, roomId, chatMessageDto), HttpStatus.OK);
    }

}
