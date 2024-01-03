package com.happiday.Happi_Day.domain.entity.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class EventCreateDto {

    public EventCreateDto() {
    }

    public EventCreateDto(String title, LocalDateTime startTime, LocalDateTime endTime, String description, String address, String location, List<String> hashtags) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.address = address;
        this.location = location;
        this.hashtags = hashtags;
    }

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "이벤트 시작일을 입력해주세요.")
    private LocalDateTime startTime;

    @NotNull(message = "이벤트 종료일을 입력해주세요.")
    private LocalDateTime endTime;

    @NotBlank(message = "내용을 입력해주세요.")
    private String description;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "장소를 입력해주세요.")
    private String location;

    @NotEmpty(message = "해시태그를 입력해주세요.")
    private List<String> hashtags;

}
