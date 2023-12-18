package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.article.Hashtag;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final TeamRepository teamRepository;
    private final FileUtils fileUtils;


    @Transactional
    public EventResponseDto createEvent(
            EventCreateDto request, MultipartFile thumbnailFile, MultipartFile imageFile, String username) {

        String thumbnailUrl;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        if (thumbnailFile == null || thumbnailFile.isEmpty()) {
            thumbnailUrl = fileUtils.defaultThumbnail(thumbnailFile);
        } else {
            thumbnailUrl = fileUtils.uploadFile(thumbnailFile);
        }

        String imageUrl = fileUtils.uploadFile(imageFile);

        List<String> hashtagRequests = request.getHashtags();


        List<Artist> artists = new ArrayList<>();
        List<Team> teams = new ArrayList<>();
        List<Hashtag> hashtagList = new ArrayList<>();

        for (String hashtagRequest : hashtagRequests) {
            Optional<Artist> existingArtist = artistRepository.findByName(hashtagRequest);
            Optional<Team> existingTeam = teamRepository.findByName(hashtagRequest);
            if (existingArtist.isPresent()) {
                artists.add(existingArtist.get());
            } else if (existingTeam.isPresent()) {
                teams.add(existingTeam.get());
            } else {
                Hashtag newHashtag = Hashtag.builder()
                        .tag(hashtagRequest)
                        .build();
                hashtagList.add(newHashtag);
            }
        }




        Event event = Event.builder()
                .user(user)
                .title(request.getTitle())
                .imageUrl(imageUrl)
                .thumbnailUrl(thumbnailUrl)
                .artists(artists)
                .teams(teams)
                .hashtags(hashtagList)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .description(request.getDescription())
                .address(request.getAddress())
                .location(request.getLocation())
                .build();

        eventRepository.save(event);
        return EventResponseDto.fromEntity(event);
    }

    public Page<EventListResponseDto> readEvents(Pageable pageable, String filter, String keyword) {
        log.info("이벤트 리스트 조회");
        Page<Event> events = eventRepository.findEventsByFilterAndKeyword(pageable, filter, keyword);

        return events.map(EventListResponseDto::fromEntity);
    }

    public Page<EventListResponseDto> readOngoingEvents(Pageable pageable, String filter, String keyword) {
        log.info("진행중인 이벤트 리스트 조회");
        Page<Event> events = eventRepository.findEventsByFilterAndKeywordAndOngoing(pageable, filter, keyword);

        return events.map(EventListResponseDto::fromEntity);
    }

    public Page<EventListResponseDto> readEventsBySubscribedArtists(Pageable pageable, String filter, String keyword, String username) {
        log.info("내가 구독한 아티스트의 이벤트 리스트 조회");
        log.info("username : " + username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Page<Event> events = eventRepository.findEventsByFilterAndKeywordAndSubscribedArtists(pageable, filter, keyword, user);

        return events.map(EventListResponseDto::fromEntity);
    }

    public Page<EventListResponseDto> readOngoingEventsBySubscribedArtists(
            Pageable pageable, String filter, String keyword, String username) {

        log.info("내가 구독한 아티스트의 진행중인 이벤트 리스트 조회");

        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Page<Event> events = eventRepository.findEventsByFilterAndKeywordAndOngoingAndSubscribedArtists(pageable, filter, keyword, user);

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

            List<String> hashtagRequests = request.getHashtags();


            List<Artist> artists = new ArrayList<>();
            List<Team> teams = new ArrayList<>();
            List<Hashtag> hashtagList = new ArrayList<>();

            for (String hashtagRequest : hashtagRequests) {
                Optional<Artist> existingArtist = artistRepository.findByName(hashtagRequest);
                Optional<Team> existingTeam = teamRepository.findByName(hashtagRequest);
                if (existingArtist.isPresent()) {
                    artists.add(existingArtist.get());
                } else if (existingTeam.isPresent()) {
                    teams.add(existingTeam.get());
                } else {
                    Hashtag newHashtag = Hashtag.builder()
                            .tag(hashtagRequest)
                            .build();
                    hashtagList.add(newHashtag);
                }
            }


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
                .hashtags(hashtagList)
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

    // 조회수
    @Transactional
    public int updateViewCounts(Long eventId) {
        return eventRepository.updateViewCount(eventId);
    }

}
