package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.chat.ChatRoom;
import com.happiday.Happi_Day.domain.entity.chat.dto.ChatMessageDto;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import com.happiday.Happi_Day.domain.repository.ChatMessageRepository;
import com.happiday.Happi_Day.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public List<ChatMessageDto> getChatMessages(String username, Long roomId) {
        User sender = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return chatMessageRepository.findAllByChatRoom_IdOrderByIdDesc(roomId)
                .stream().map(ChatMessageDto::fromEntity).toList();
    }

    public ChatMessageDto sendMessage(String username, Long roomId, ChatMessageDto dto) {
        User sender = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ChatMessageDto payload = ChatMessageDto.fromEntity(chatMessageRepository.save(dto.newEntity(sender, chatRoom)));
        User receiver = chatRoom.getReceiver();
        messagingTemplate.convertAndSend(
                String.format(String.format("/pub/%s", receiver.getId())),
                payload
        );
        messagingTemplate.convertAndSend(
                String.format(String.format("/pub/%s", sender.getId())),
                payload
        );

        return payload;
    }
}
