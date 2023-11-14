package com.happiday.Happi_Day.domain.entity.artist.dto;

import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.artist.ArtistType;
import lombok.Data;

import java.util.List;

@Data
public class ArtistUpdateDto {
    private String name;
    private ArtistType type;
    private String description;
    private String nationality;
    private List<Long> teamIds;

    public Artist toEntity() {
        return Artist.builder()
                .name(name)
                .type(type)
                .description(description)
                .nationality(nationality)
                .build();
    }
}
