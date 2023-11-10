package com.happiday.Happi_Day.domain.controller;

import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistListResponseDto;
import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistRegisterDto;
import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistDetailResponseDto;
import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistUpdateDto;
import com.happiday.Happi_Day.domain.entity.event.dto.EventListResponseDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamListResponseDto;
import com.happiday.Happi_Day.domain.entity.product.dto.SalesListResponseDto;
import com.happiday.Happi_Day.domain.service.ArtistService;
import com.happiday.Happi_Day.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<ArtistDetailResponseDto> registerArtist(@RequestPart(name = "artist") ArtistRegisterDto requestDto,
                                                                  @RequestPart(value = "file", required = false) MultipartFile imageFile) {
        String username = SecurityUtils.getCurrentUsername();
        ArtistDetailResponseDto responseDto = artistService.registerArtist(requestDto, imageFile);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{artistId}")
    public ResponseEntity<ArtistDetailResponseDto> updateArtist(@PathVariable Long artistId,
                                                                @RequestPart(name = "artist") ArtistUpdateDto requestDto,
                                                                @RequestPart(value = "file", required = false) MultipartFile imageFile) {
        String username = SecurityUtils.getCurrentUsername();
        ArtistDetailResponseDto responseDto = artistService.updateArtist(artistId, requestDto, imageFile);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long artistId) {
        String username = SecurityUtils.getCurrentUsername();
        artistService.delete(artistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistDetailResponseDto> getArtist(@PathVariable Long artistId) {
        ArtistDetailResponseDto responseDto = artistService.getArtist(artistId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ArtistListResponseDto>> getArtists() {
        List<ArtistListResponseDto> responseDtos = artistService.getArtists();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{artistId}/teams")
    public ResponseEntity<List<TeamListResponseDto>> getArtistTeams(@PathVariable Long artistId) {
        List<TeamListResponseDto> artistTeams = artistService.getArtistTeams(artistId);
        return new ResponseEntity<>(artistTeams, HttpStatus.OK);
    }

    @GetMapping("/{artistId}/sales")
    public ResponseEntity<List<SalesListResponseDto>> getArtistSales(@PathVariable Long artistId) {
        List<SalesListResponseDto> responseDtos = artistService.getSalesList(artistId);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{artistId}/events")
    public ResponseEntity<List<EventListResponseDto>> getArtistEvents(@PathVariable Long artistId) {
        List<EventListResponseDto> responseDtos = artistService.getEvents(artistId);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
}
