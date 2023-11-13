package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistListResponseDto;
import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistRegisterDto;
import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistDetailResponseDto;
import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistUpdateDto;
import com.happiday.Happi_Day.domain.entity.event.dto.EventListResponseDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamListResponseDto;
import com.happiday.Happi_Day.domain.entity.product.dto.SalesListResponseDto;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.ArtistRepository;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import com.happiday.Happi_Day.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final FileUtils fileUtils;

    // 아티스트 등록
    @Transactional
    public ArtistDetailResponseDto registerArtist(ArtistRegisterDto requestDto, MultipartFile imageFile) {
        Artist artistEntity = requestDto.toEntity();

        // 이미지 저장 로직
        if (imageFile != null && !imageFile.isEmpty()) {
            String saveFileUrl = fileUtils.uploadFile(imageFile);
            artistEntity.setProfileUrl(saveFileUrl);
        }

        artistEntity = artistRepository.save(artistEntity);
        return ArtistDetailResponseDto.of(artistEntity, false);
    }

    // 아티스트 정보 수정
    @Transactional
    public ArtistDetailResponseDto updateArtist(Long artistId, ArtistUpdateDto requestDto, MultipartFile imageFile) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        // 이미지 저장 및 기존 이미지 삭제 로직
        if (imageFile != null && !imageFile.isEmpty()) {
            // 기존 이미지가 있다면 삭제
            if (artist.getProfileUrl() != null && !artist.getProfileUrl().isEmpty()) {
                try {
                    fileUtils.deleteFile(artist.getProfileUrl());
                    log.info("이미지 삭제 완료: " + artist.getProfileUrl());
                } catch (Exception e) {
                    log.error("이미지 삭제 실패: " + artist.getProfileUrl(), e);
                    throw new CustomException(ErrorCode.FILE_DELETE_BAD_REQUEST);
                }
            }

            // 새로운 이미지 업로드
            String saveFileUrl = fileUtils.uploadFile(imageFile);
            artist.setProfileUrl(saveFileUrl);
            log.info("이미지 업데이트: " + saveFileUrl);
        }

        artist.update(requestDto.toEntity());
        artistRepository.save(artist);

        return ArtistDetailResponseDto.of(artist, false);
    }

    // 아티스트 삭제
    @Transactional
    public void delete(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));
        artistRepository.delete(artist);
    }

    // 아티스트 상세 조회
    public ArtistDetailResponseDto getArtist(Long artistId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        // 구독 여부 확인
        boolean isSubscribed = user.getSubscribedArtists().contains(artist);

        // 아티스트가 속한 팀 정보 가져오기
        List<TeamListResponseDto> teams = artist.getTeams().stream()
                .map(TeamListResponseDto::of)
                .collect(Collectors.toList());

        return ArtistDetailResponseDto.of(artist, isSubscribed, teams);
    }

    // 아티스트 목록 조회
    public List<ArtistListResponseDto> getArtists() {
        return artistRepository.findAll().stream()
                .map(ArtistListResponseDto::of)
                .collect(Collectors.toList());
    }

    // 아티스트가 속한 팀 목록 조회
    public List<TeamListResponseDto> getArtistTeams(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        return artist.getTeams().stream()
                .map(TeamListResponseDto::of)
                .collect(Collectors.toList());
    }

    // 아티스트 관련 상품 목록 조회
    public List<SalesListResponseDto> getSalesList(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        return artist.getSalesList().stream()
                .map(SalesListResponseDto::of)
                .collect(Collectors.toList());
    }

    // 아티스트 이벤트 목록 조회
    public List<EventListResponseDto> getEvents(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        return artist.getEvents().stream()
                .map(EventListResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 아티스트 구독 추가
    @Transactional
    public void subscribeToArtist(String username, Long artistId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));
        if (!user.getSubscribedArtists().contains(artist)) {
            user.getSubscribedArtists().add(artist);
            userRepository.save(user);
        }
    }

    // 아티스트 구독 취소
    @Transactional
    public void unsubscribeFromArtist(String username, Long artistId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));
        user.getSubscribedArtists().remove(artist);
        userRepository.save(user);
    }
}
