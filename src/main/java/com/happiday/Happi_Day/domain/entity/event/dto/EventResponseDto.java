package com.happiday.Happi_Day.domain.entity.event.dto;

import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.team.Team;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class EventResponseDto {

    private Long id;

    private String username;

    private String title;

    private LocalDateTime updatedAt;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String description;

    private String address;

    private String location;

    private String thumbnailUrl;

    private String imageUrl;

    private List<String> artists;

    private List<String> teams;

    private int commentCount;

    private int likeCount;

    private int joinCount;


    public static EventResponseDto fromEntity(Event event) {
        // 이벤트의 artists 필드에서 이름을 꺼내오기
        List<String> eventArtists = event.getArtists().stream().map(Artist::getName).collect(Collectors.toList());

        // 이벤트의 ectArtists 필드가 null이 아닌 경우, 추가 아티스트들을 쉼표(,)로 분리하여 리스트로 만들기
        List<String> additionalArtists = event.getEctArtists() != null ? Arrays.asList(event.getEctArtists().split(", ")) : Collections.emptyList();

        // eventArtists와 additionalArtists를 합친 리스트 생성
        List<String> allArtists = new ArrayList<>(eventArtists);
        allArtists.addAll(additionalArtists);

        // 이벤트의 teams 필드에서 이름을 꺼내오기
        List<String> eventTeams = event.getTeams().stream().map(Team::getName).collect(Collectors.toList());

        // 이벤트의 ectTeams 필드가 null이 아닌 경우, 추가 팀들을 쉼표(,)로 분리하여 리스트로 만들기
        List<String> additionalTeams = event.getEctTeams() != null ? Arrays.asList(event.getEctTeams().split(", ")) : Collections.emptyList();

        // eventTeams와 additionalTeams를 합친 리스트 생성
        List<String> allTeams = new ArrayList<>(eventTeams);
        allTeams.addAll(additionalTeams);

        return EventResponseDto.builder()
                .id(event.getId())
                .username(event.getUser().getNickname())
                .title(event.getTitle())
                .updatedAt(event.getUpdatedAt())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .description(event.getDescription())
                .address(event.getAddress())
                .location(event.getLocation())
                .thumbnailUrl(event.getThumbnailUrl())
                .imageUrl(event.getImageUrl())
                .artists(allArtists)
                .teams(allTeams)
                .commentCount(event.getCommentCount())
                .joinCount(event.getJoinCount())
                .likeCount(event.getLikeCount())
                .build();
    }
}
