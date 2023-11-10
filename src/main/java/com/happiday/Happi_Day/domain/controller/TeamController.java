package com.happiday.Happi_Day.domain.controller;

import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistListResponseDto;
import com.happiday.Happi_Day.domain.entity.event.dto.EventListResponseDto;
import com.happiday.Happi_Day.domain.entity.product.dto.SalesListResponseDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamListResponseDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamRegisterDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamDetailResponseDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamUpdateDto;
import com.happiday.Happi_Day.domain.service.TeamService;
import com.happiday.Happi_Day.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamDetailResponseDto> registerTeam(@RequestPart(name = "team") TeamRegisterDto requestDto,
                                                              @RequestPart(value = "file", required = false) MultipartFile imageFile) {
        String username = SecurityUtils.getCurrentUsername();
        TeamDetailResponseDto responseDto = teamService.registerTeam(requestDto, imageFile);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamDetailResponseDto> updateTeam(@PathVariable Long teamId,
                                                            @RequestPart(name = "team") TeamUpdateDto requestDto,
                                                            @RequestPart(value = "file", required = false) MultipartFile imageFile) {
        String username = SecurityUtils.getCurrentUsername();
        TeamDetailResponseDto responseDto = teamService.updateTeam(teamId, requestDto, imageFile);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        String username = SecurityUtils.getCurrentUsername();
        teamService.deleteTeam(teamId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDetailResponseDto> getTeam(@PathVariable Long teamId) {
        TeamDetailResponseDto responseDto = teamService.getTeam(teamId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TeamListResponseDto>> getTeams() {
        List<TeamListResponseDto> responseDtos = teamService.getTeams();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{teamId}/artists")
    public ResponseEntity<List<ArtistListResponseDto>> getArtistsByTeam(@PathVariable Long teamId) {
        List<ArtistListResponseDto> teamArtists = teamService.getTeamArtists(teamId);
        return new ResponseEntity<>(teamArtists, HttpStatus.OK);
    }

    @GetMapping("/{teamId}/sales")
    public ResponseEntity<List<SalesListResponseDto>> getTeamSales(@PathVariable Long teamId) {
        List<SalesListResponseDto> responseDtos = teamService.getSalesList(teamId);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{teamId}/events")
    public ResponseEntity<List<EventListResponseDto>> getArtistEvents(@PathVariable Long teamId) {
        List<EventListResponseDto> responseDtos = teamService.getEvents(teamId);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
}