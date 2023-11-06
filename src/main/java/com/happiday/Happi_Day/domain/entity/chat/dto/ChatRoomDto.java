package com.happiday.Happi_Day.domain.entity.chat.dto;

import com.happiday.Happi_Day.domain.entity.chat.ChatRoom;
import com.happiday.Happi_Day.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomDto{

    private Long id;
    private String sender;
    private String receiver;

    public ChatRoom fromEntity(ChatRoom chatRoom, User sender, User receiver) {
        return ChatRoom.builder()
                .id(chatRoom.getId())
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
