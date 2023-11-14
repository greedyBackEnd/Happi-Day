package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistListResponseDto;
import com.happiday.Happi_Day.domain.entity.event.dto.EventListResponseDto;
import com.happiday.Happi_Day.domain.entity.product.dto.SalesListResponseDto;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamListResponseDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamRegisterDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamDetailResponseDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamUpdateDto;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.TeamRepository;
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
public class TeamService {

    private final TeamRepository teamRepository;
    private final FileUtils fileUtils;
    private final UserRepository userRepository;

    // 팀 등록
    @Transactional
    public TeamDetailResponseDto registerTeam(TeamRegisterDto requestDto, MultipartFile imageFile) {
        Team teamEntity = requestDto.toEntity();

        // 이미지 저장 로직
        if (imageFile != null && !imageFile.isEmpty()) {
            String saveFileUrl = fileUtils.uploadFile(imageFile);
            teamEntity.setLogoUrl(saveFileUrl);
        }

        teamEntity = teamRepository.save(teamEntity);
        return TeamDetailResponseDto.of(teamEntity, false);
    }

    // 팀 정보 수정
    @Transactional
    public TeamDetailResponseDto updateTeam(Long teamId, TeamUpdateDto requestDto, MultipartFile imageFile, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        // 이미지 저장 및 기존 이미지 삭제 로직
        if (imageFile != null && !imageFile.isEmpty()) {
            // 기존 이미지가 있다면 삭제
            if (team.getLogoUrl() != null && !team.getLogoUrl().isEmpty()) {
                try {
                    fileUtils.deleteFile(team.getLogoUrl());
                    log.info("이미지 삭제 완료: " + team.getLogoUrl());
                } catch (Exception e) {
                    log.error("이미지 삭제 실패: " + team.getLogoUrl(), e);
                    throw new CustomException(ErrorCode.FILE_DELETE_BAD_REQUEST);
                }
            }

            // 새로운 이미지 업로드
            String saveFileUrl = fileUtils.uploadFile(imageFile);
            team.setLogoUrl(saveFileUrl);
            log.info("이미지 업데이트: " + saveFileUrl);
        }

        team.update(requestDto.toEntity());
        teamRepository.save(team);

        boolean isSubscribed = user.getSubscribedTeams().contains(team);
        // 팀에 소속된 아티스트 정보 가져오기
        List<ArtistListResponseDto> artists = team.getArtists().stream()
                .map(ArtistListResponseDto::of)
                .collect(Collectors.toList());
        return TeamDetailResponseDto.of(team, isSubscribed, artists);
    }

    // 팀 삭제
    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));
        teamRepository.delete(team);
    }

    // 팀 상세 조회
    public TeamDetailResponseDto getTeam(Long teamId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));
        boolean isSubscribed = user.getSubscribedTeams().contains(team);
        // 팀에 소속된 아티스트 정보 가져오기
        List<ArtistListResponseDto> artists = team.getArtists().stream()
                .map(ArtistListResponseDto::of)
                .collect(Collectors.toList());
        return TeamDetailResponseDto.of(team, isSubscribed, artists);
    }

    // 팀 목록 조회
    public List<TeamListResponseDto> getTeams() {
        return teamRepository.findAll().stream()
                .map(TeamListResponseDto::of)
                .collect(Collectors.toList());
    }

    // 팀 소속 아티스트 목록 조회
    public List<ArtistListResponseDto> getTeamArtists(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        return team.getArtists().stream()
                .map(ArtistListResponseDto::of)
                .collect(Collectors.toList());
    }

    // 팀 관련 상품 목록 조회
    public List<SalesListResponseDto> getSalesList(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        return team.getSalesList().stream()
                .map(SalesListResponseDto::of)
                .collect(Collectors.toList());
    }

    // 팀의 이벤트 목록 조회
    public List<EventListResponseDto> getEvents(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        return team.getEvents().stream()
                .map(EventListResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 팀 구독
    @Transactional
    public void subscribeToTeam(String username, Long teamId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));
        if (!user.getSubscribedTeams().contains(team)) {
            user.getSubscribedTeams().add(team);
            userRepository.save(user);
        }
    }

    // 팀 구독 취소
    @Transactional
    public void unsubscribeFromTeam(String username, Long teamId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));
        user.getSubscribedTeams().remove(team);
        userRepository.save(user);
    }
}
