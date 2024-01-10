package com.happiday.Happi_Day.domain.controller;

import com.happiday.Happi_Day.utils.DefaultImageUtils;
import com.happiday.Happi_Day.utils.defaultImageDto.DefaultImageUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/default/images")
@RequiredArgsConstructor
public class DefaultImagesController {

    private final DefaultImageUtils defaultImageUtils;

    @GetMapping("/article")
    public ResponseEntity<DefaultImageUrlResponseDto> getArticleThumbnail() {
        String articleThumbnail = defaultImageUtils.getDefaultImageUrlArticleThumbnail();
        return new ResponseEntity<>(DefaultImageUrlResponseDto.of(articleThumbnail), HttpStatus.OK);
    }

    @GetMapping("/event")
    public ResponseEntity<DefaultImageUrlResponseDto> getEventThumbnail() {
        String eventThumbnail = defaultImageUtils.getDefaultImageUrlEventThumbnail();
        return new ResponseEntity<>(DefaultImageUrlResponseDto.of(eventThumbnail), HttpStatus.OK);
    }

    @GetMapping("/sales")
    public ResponseEntity<DefaultImageUrlResponseDto> getSalesThumbnail() {
        String salesThumbnail = defaultImageUtils.getDefaultImageUrlSalesThumbnail();
        return new ResponseEntity<>(DefaultImageUrlResponseDto.of(salesThumbnail), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<DefaultImageUrlResponseDto> getUserProfile() {
        String userProfile = defaultImageUtils.getDefaultImageUrlUserProfile();
        return new ResponseEntity<>(DefaultImageUrlResponseDto.of(userProfile), HttpStatus.OK);
    }

    @GetMapping("/teamArtist")
    public ResponseEntity<DefaultImageUrlResponseDto> getTeamArtistProfile() {
        String teamArtistProfile = defaultImageUtils.getDefaultImageUrlTeamArtistProfile();
        return new ResponseEntity<>(DefaultImageUrlResponseDto.of(teamArtistProfile), HttpStatus.OK);
    }
}
