package com.happiday.Happi_Day.utils;

import com.happiday.Happi_Day.domain.entity.article.Hashtag;
import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.repository.ArtistRepository;
import com.happiday.Happi_Day.domain.repository.HashtagRepository;
import com.happiday.Happi_Day.domain.repository.TeamRepository;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HashtagUtils {

    private final ArtistRepository artistRepository;
    private final TeamRepository teamRepository;
    private final HashtagRepository hashtagRepository;

    public HashtagUtils(ArtistRepository artistRepository, TeamRepository teamRepository, HashtagRepository hashtagRepository) {
        this.artistRepository = artistRepository;
        this.teamRepository = teamRepository;
        this.hashtagRepository = hashtagRepository;
    }

    public Triple<List<Artist>, List<Team>, List<Hashtag>> processTags(List<String> hashtagRequests) {
        List<Artist> artists = new ArrayList<>();
        List<Team> teams = new ArrayList<>();
        List<Hashtag> hashtags = new ArrayList<>();

        hashtagRequests.forEach(hashtagRequest -> {
            artistRepository.findByName(hashtagRequest)
                    .ifPresent(artists::add);

            teamRepository.findByName(hashtagRequest)
                    .ifPresent(teams::add);

            hashtagRepository.findByTag(hashtagRequest)
                    .ifPresent(hashtags::add);
        });

        return Triple.of(artists, teams, hashtags);
    }
}


