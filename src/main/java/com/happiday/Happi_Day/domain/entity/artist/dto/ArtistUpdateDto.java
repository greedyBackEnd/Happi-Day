package com.happiday.Happi_Day.domain.entity.artist.dto;

import com.happiday.Happi_Day.domain.entity.artist.Artist;
import lombok.Data;

import java.util.List;

@Data
public class ArtistUpdateDto {
    private String name;
    private String description;
    private List<Long> teamIds;

    public Artist toEntity() {
        return Artist.builder()
                .name(name)
                .description(description)
                .build();
    }
}
