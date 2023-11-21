package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamInitService {

    private final TeamRepository teamRepository;

    public void initTeams() {
        List<Team> teams = List.of(
                Team.builder()
                        .name("동방신기")
                        .description("동방신기입니다.")
                        .build(),
                Team.builder()
                        .name("god")
                        .description("god입니다.")
                        .build()
        );

        teams.forEach(team -> {
            if (!teamRepository.existsByName(team.getName())) {
                teamRepository.save(team);
            }
        });
    }
}
