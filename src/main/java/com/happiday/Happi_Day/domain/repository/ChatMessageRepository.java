package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatRoom_IdOrderByIdDesc(Long roomId);
}
