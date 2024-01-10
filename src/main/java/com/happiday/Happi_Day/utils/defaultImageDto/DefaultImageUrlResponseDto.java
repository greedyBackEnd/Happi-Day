package com.happiday.Happi_Day.utils.defaultImageDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultImageUrlResponseDto {
    private String defaultImageUrl;

    public static DefaultImageUrlResponseDto of(String url) {
        return DefaultImageUrlResponseDto.builder()
                .defaultImageUrl(url)
                .build();
    }
}
