package com.happiday.Happi_Day.domain.controller;

import com.happiday.Happi_Day.domain.entity.event.dto.EventResponseDto;
import com.happiday.Happi_Day.domain.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventMapController {
    private final EventService eventService;

    @GetMapping("/detail/{id}")
    public String eventDetail(@PathVariable Long id, Model model) {
        EventResponseDto responseDto = eventService.readMapEvent(id);
        model.addAttribute("event", responseDto);
        return "map-test";
    }

}



