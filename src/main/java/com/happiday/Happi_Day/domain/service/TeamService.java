package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamRegisterDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamResponseDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamUpdateDto;
import com.happiday.Happi_Day.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public TeamResponseDto registerTeam(TeamRegisterDto requestDto, MultipartFile imageFile) {

        // TODO 이미지 저장 로직

        Team team = requestDto.toEntity();
        team = teamRepository.save(team);
        return TeamResponseDto.of(team);
    }

    @Transactional
    public TeamResponseDto updateTeam(Long teamId, TeamUpdateDto requestDto, MultipartFile imageFile) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team을 찾을 수 없습니다."));

        // TODO 이미지 저장 로직

        team.update(requestDto.toEntity());
        teamRepository.save(team);

        return TeamResponseDto.of(team);
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team을 찾을 수 없습니다."));
        teamRepository.delete(team);
    }

    public TeamResponseDto getTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team을 찾을 수 없습니다."));
        return TeamResponseDto.of(team);
    }

    public List<TeamResponseDto> getTeams() {
        return teamRepository.findAll().stream()
                .map(TeamResponseDto::of)
                .collect(Collectors.toList());
    }
}