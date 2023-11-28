package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.event.dto.EventCreateDto;
import com.happiday.Happi_Day.domain.entity.event.dto.EventListResponseDto;
import com.happiday.Happi_Day.domain.entity.event.dto.EventResponseDto;
import com.happiday.Happi_Day.domain.entity.event.dto.EventUpdateDto;
import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.*;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import com.happiday.Happi_Day.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final TeamRepository teamRepository;
    private final QueryRepository queryRepository;
    private final FileUtils fileUtils;


    @Transactional
    public EventResponseDto createEvent(
            EventCreateDto request, MultipartFile thumbnailFile, MultipartFile imageFile, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String thumbnailUrl;

        if (thumbnailFile == null || thumbnailFile.isEmpty()) {
            thumbnailUrl = fileUtils.defaultThumbnail(thumbnailFile);
        } else {
            thumbnailUrl = fileUtils.uploadFile(thumbnailFile);
        }

        String imageUrl = fileUtils.uploadFile(imageFile);

        // Artist Entity에 없는 Artist 처리
        List<String> artistNameList = request.getArtists();
        List<Artist> artists = new ArrayList<>();
        List<String> ectArtists = new ArrayList<>(); // 존재하지 않는 아티스트 이름 목록

        for (String artistName : artistNameList) {
            Optional<Artist> existingArtist = artistRepository.findByName(artistName);
            if (existingArtist.isPresent()) {
                artists.add(existingArtist.get());
            } else {
                // Artist 엔티티가 존재하지 않을 경우
                ectArtists.add(artistName);
            }
        }

        // Team Entity에 없는 Team 처리
        List<String> teamNameList = request.getTeams();
        List<Team> teams = new ArrayList<>();
        List<String> ectTeams = new ArrayList<>(); // 존재하지 않는 팀 이름 목록

        for (String teamName : teamNameList) {
            Optional<Team> existingTeam = teamRepository.findByName(teamName);
            if (existingTeam.isPresent()) {
                teams.add(existingTeam.get());
            } else {
                ectTeams.add(teamName);
            }
        }

        String ectArtist = String.join(", ", ectArtists); // 존재하지 않는 아티스트 이름을 쉼표로 구분하여 저장
        String ectTeam = String.join(", ", ectTeams); // 존재하지 않는 팀 이름을 쉼표로 구분하여 저장

        Event event = Event.builder()
                .user(user)
                .title(request.getTitle())
                .imageUrl(imageUrl)
                .thumbnailUrl(thumbnailUrl)
                .artists(artists)
                .teams(teams)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .description(request.getDescription())
                .address(request.getAddress())
                .location(request.getLocation())
                .build();

        // ect Artist, Team이 있는 경우 추가

        if (!ectArtists.isEmpty()) {
            event = event.toBuilder()
                    .ectArtists(ectArtist)
                    .build();
        }
        if (!ectTeams.isEmpty()) {
            event = event.toBuilder()
                    .ectTeams(ectTeam)
                    .build();
        }

        eventRepository.save(event);
        return EventResponseDto.fromEntity(event);
    }

    public Page<EventListResponseDto> readEvents(Pageable pageable, String filter, String keyword) {
        log.info("이벤트 리스트 조회");
        Page<Event> events = queryRepository.findEventsByFilterAndKeyword(pageable, filter, keyword);

        return events.map(EventListResponseDto::fromEntity);
    }

    public EventResponseDto readEvent(Long eventId) {
        log.info("이벤트 단일 조회");
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));
        return EventResponseDto.fromEntity(event);
    }

    @Transactional
    public EventResponseDto updateEvent(Long eventId, EventUpdateDto request, MultipartFile thumbnailFile, MultipartFile imageFile, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.getUsername().equals(username)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        // 이미지 업로드 추가
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            fileUtils.deleteFile(event.getThumbnailUrl());
            String newThumbnailUrl = fileUtils.uploadFile(thumbnailFile);
            event.setThumbnailUrl(newThumbnailUrl);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            fileUtils.deleteFile(event.getImageUrl());
            String newImageUrl = fileUtils.uploadFile(imageFile);
            event.setImageUrl(newImageUrl);
        }

        // Artist Entity에 없는 Artist 처리
        List<String> artistNameList = request.getArtists();
        List<Artist> artists = new ArrayList<>();
        List<String> ectArtists = new ArrayList<>(); // 존재하지 않는 아티스트 이름 목록

        for (String artistName : artistNameList) {
            Optional<Artist> existingArtist = artistRepository.findByName(artistName);
            if (existingArtist.isPresent()) {
                artists.add(existingArtist.get());
            } else {
                // Artist 엔티티가 존재하지 않을 경우
                ectArtists.add(artistName);
            }
        }

        // Team Entity에 없는 Team 처리
        List<String> teamNameList = request.getTeams();
        List<Team> teams = new ArrayList<>();
        List<String> ectTeams = new ArrayList<>(); // 존재하지 않는 팀 이름 목록

        for (String teamName : teamNameList) {
            Optional<Team> existingTeam = teamRepository.findByName(teamName);
            if (existingTeam.isPresent()) {
                teams.add(existingTeam.get());
            } else {
                ectTeams.add(teamName);
            }
        }

        String ectArtist = String.join(", ", ectArtists); // 존재하지 않는 아티스트 이름을 쉼표로 구분하여 저장
        String ectTeam = String.join(", ", ectTeams); // 존재하지 않는 팀 이름을 쉼표로 구분하여 저장

        event.update(Event.builder()
                .user(user)
                .title(request.getTitle())
                .artists(artists)
                .teams(teams)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .description(request.getDescription())
                .location(request.getLocation())
                .address(request.getAddress())
                .ectArtists(ectArtists.isEmpty() ? event.getEctArtists() : ectArtist)
                .ectTeams(ectTeams.isEmpty() ? event.getEctTeams() : ectTeam)
                .build());

        eventRepository.save(event);
        return EventResponseDto.fromEntity(event);
    }

    @Transactional
    public void deleteEvent(Long eventId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.getUsername().equals(username)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));
        eventRepository.delete(event);
    }

    @Transactional
    public String likeEvent(Long eventId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        boolean isLiked = event.getLikes().contains(user);

        String response = "";
        if (isLiked) {
            // 이미 좋아요를 한 경우, 좋아요 취소
            user.getEventLikes().remove(event);
            event.getLikes().remove(user);
            response = "좋아요 취소";
        } else {
            // 좋아요를 하지 않은 경우, 좋아요
            user.getEventLikes().add(event);
            event.getLikes().add(user);
            response = "좋아요 성공";
        }

        eventRepository.save(event);
        return response + " / 좋아요 개수 : " + event.getLikeCount();
    }

    @Transactional
    public String joinEvent(Long eventId, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        boolean isJoined = event.getJoinList().contains(user);

        log.info(String.valueOf(isJoined));
        String response = "";
        if (isJoined) {
            // 이미 참여한 경우, 취소
            user.getEventJoinList().remove(event);
            event.getJoinList().remove(user);
            response = " 이벤트 참여 취소";
        } else {
            // 참여하지 않은 경우, 참여
            user.getEventJoinList().add(event);
            event.getJoinList().add(user);
            response = " 이벤트 참여";
        }

        eventRepository.save(event);
        return event.getTitle() + response;
    }

    public Page<EventListResponseDto> readEventsByArtists(String username, Pageable pageable, String filter, String keyword) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        log.info("이벤트 리스트 조회");
        Page<Event> events = queryRepository.findEventsByArtist(username, pageable, filter, keyword);

        return events.map(EventListResponseDto::fromEntity);
    }
}
