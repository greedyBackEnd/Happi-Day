package com.happiday.Happi_Day.domain.entity.event.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class EventUpdateDto {

    public EventUpdateDto() {
    }
    public EventUpdateDto(String title, LocalDateTime startTime, LocalDateTime endTime, String description, String address, String location, String thumbnailUrl, String imageUrl, List<String> hashtags) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.address = address;
        this.location = location;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
        this.hashtags = hashtags;
    }

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String description;

    private String address;

    private String location;

    private String thumbnailUrl;

    private String imageUrl;

    private List<String> hashtags;

}
