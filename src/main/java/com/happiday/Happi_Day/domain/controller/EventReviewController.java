package com.happiday.Happi_Day.domain.controller;


import com.happiday.Happi_Day.domain.entity.event.dto.review.EventReviewCreateDto;
import com.happiday.Happi_Day.domain.entity.event.dto.review.EventReviewResponseDto;
import com.happiday.Happi_Day.domain.service.EventReviewService;
import com.happiday.Happi_Day.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/events/{eventId}/reviews")
@RequiredArgsConstructor
@Slf4j
public class EventReviewController {
    private final EventReviewService reviewService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EventReviewResponseDto> createReview(
            @PathVariable("eventId") Long eventId,
            @Valid @RequestPart(value = "review")EventReviewCreateDto reviewCreateDto,
            @RequestPart(value = "imageFile") MultipartFile imageFile
            ){
        String username = SecurityUtils.getCurrentUsername();
        EventReviewResponseDto responseDto = reviewService.createReview(eventId, reviewCreateDto, imageFile, username);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
