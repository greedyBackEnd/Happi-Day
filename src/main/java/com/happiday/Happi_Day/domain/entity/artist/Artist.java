package com.happiday.Happi_Day.domain.entity.artist;

import com.happiday.Happi_Day.domain.entity.BaseEntity;
import com.happiday.Happi_Day.domain.entity.article.Article;
import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class Artist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArtistType type = ArtistType.SOLO;

    private String description;

    private String profileUrl;

    private String nationality;

    // 팀-아티스트
    @ManyToMany
    @JoinTable(
            name = "artist_team",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();

    // 이벤트
    @ManyToMany(mappedBy = "artists")
    private List<Event> events = new ArrayList<>();

    // 판매글
    @ManyToMany
    @JoinTable(
            name = "artist_sales",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "sales_id")
    )
    private List<Sales> salesList = new ArrayList<>();

    // 유저 구독
    @ManyToMany(mappedBy = "subscribedArtists")
    private List<User> subscribers = new ArrayList<>();

    // 게시판
    @ManyToMany(mappedBy = "artists")
    private List<Article> articles = new ArrayList<>();

    public void update(Artist artistUpdate) {
        this.name = artistUpdate.getName();
        this.type = artistUpdate.getType();
        this.description = artistUpdate.getDescription();
        this.nationality = artistUpdate.getNationality();
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setTeams(List<Team> teams) {
        if (this.teams == null) {
            this.teams = new ArrayList<>();
        }
        this.teams.clear();
        if (teams != null) {
            this.teams.addAll(teams);
        }
    }
}
