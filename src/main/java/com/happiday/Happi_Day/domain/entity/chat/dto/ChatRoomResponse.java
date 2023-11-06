package com.happiday.Happi_Day.domain.entity.chat.dto;

import com.happiday.Happi_Day.domain.entity.chat.ChatRoom;
import com.happiday.Happi_Day.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponse {
    Long id;
    String receiver;

    public static ChatRoomResponse fromEntity(ChatRoom chatRoom, User sender) {
        return ChatRoomResponse.builder()
                .id(chatRoom.getId())
                .receiver(chatRoom.getSender().equals(sender) ? chatRoom.getReceiver().getNickname() : chatRoom.getSender().getNickname())
                .build();
    }
}
