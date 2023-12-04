package com.happiday.Happi_Day.domain.repository;


import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.artist.QArtist;
import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistListResponseDto;
import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.event.QEvent;
import com.happiday.Happi_Day.domain.entity.event.dto.EventListResponseDto;
import com.happiday.Happi_Day.domain.entity.team.QTeam;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamListResponseDto;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.happiday.Happi_Day.domain.entity.artist.QArtist.artist;
import static com.happiday.Happi_Day.domain.entity.event.QEvent.event;
import static com.happiday.Happi_Day.domain.entity.team.QTeam.team;
import static com.happiday.Happi_Day.domain.entity.user.QUser.user;

@Slf4j
@RequiredArgsConstructor
@Repository
public class QueryRepository {

    private final JPAQueryFactory queryFactory;


    // 이벤트 목록
    public Page<Event> findEventsByFilterAndKeyword(Pageable pageable, String filter, String keyword) {
        List<Event> events = queryFactory
                .selectFrom(event)
                .join(event.user, user).fetchJoin()
                .where(eventSearchFilter(filter, keyword))
                .orderBy(event.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        // 카운트
        Long count = queryFactory
                .select(event.count())
                .from(event)
                .where(eventSearchFilter(filter, keyword))
                .fetchOne();

        return new PageImpl<>(events, pageable, count);
    }

    // 진행중인 이벤트 목록
    public Page<Event> findEventsByFilterAndKeywordAndOngoing(Pageable pageable, String filter, String keyword) {

        List<Event> events = queryFactory
                .selectFrom(event)
                .join(event.user, user).fetchJoin()
                .where((event.startTime.before(LocalDateTime.now()).and(event.endTime.after(LocalDateTime.now()))
                        .and(eventSearchFilter(filter, keyword))))
                .orderBy(event.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 카운트
        Long count = queryFactory
                .select(event.count())
                .from(event)
                .where((event.startTime.before(LocalDateTime.now()).and(event.endTime.after(LocalDateTime.now()))
                        .and(eventSearchFilter(filter, keyword))))
                .fetchOne();

        log.info("닉네임 : " + event.user.nickname);

        return new PageImpl<>(events, pageable, count);
    }

    // 내가 구독한 아티스트의 이벤트 목록
    public Page<Event> findEventsByFilterAndKeywordAndSubscribedArtists(Pageable pageable, String filter, String keyword, User loginUser) {

        log.info("이건 !!!! userId : " + loginUser.getId());
        log.info("이건 !!!! user.subscribedArtists : " + user.subscribedArtists.any().id);

        List<Event> events = queryFactory
                .selectFrom(event)
                .where(
                        (user.subscribedArtists.any().id.eq(loginUser.getId())
                                .or(user.subscribedTeams.any().id.eq(loginUser.getId()))
                     .and(eventSearchFilter(filter, keyword))))
                .orderBy(event.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();



        // 카운트
        Long count = queryFactory
                .select(event.count())
                .from(event)
                .where(
                        (user.subscribedArtists.any().id.eq(loginUser.getId())
                                .or(user.subscribedTeams.any().id.eq(loginUser.getId()))
                                .and(eventSearchFilter(filter, keyword)))
                )
                .fetchOne();

        log.info("닉네임 : " + event.user.nickname);

        return new PageImpl<>(events, pageable, count);
    }

    // 내가 구독한 아티스트의 진행 중인 이벤트 목록
    public Page<Event> findEventsByFilterAndKeywordAndOngoingAndSubscribedArtists(
            Pageable pageable, String filter, String keyword, User loginUser) {


        List<Event> events = queryFactory
                .selectFrom(event)
                .join(event.user, user).fetchJoin()
                .where(
                        (event.startTime.before(LocalDateTime.now()).and(event.endTime.after(LocalDateTime.now()))
                        .and(user.subscribedArtists.any().id.eq(loginUser.getId())
                                .or(user.subscribedTeams.any().id.eq(loginUser.getId()))
                        .and(eventSearchFilter(filter, keyword))))
                )
                .orderBy(event.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();



        // 카운트
        Long count = queryFactory
                .select(event.count())
                .from(event)
                .where(
                        (event.startTime.before(LocalDateTime.now()).and(event.endTime.after(LocalDateTime.now()))
                                .and(user.subscribedArtists.any().id.eq(loginUser.getId())
                                        .or(user.subscribedTeams.any().id.eq(loginUser.getId()))
                                        .and(eventSearchFilter(filter, keyword))))
                )
                .fetchOne();

        log.info("닉네임 : " + event.user.nickname);

        return new PageImpl<>(events, pageable, count);
    }



    private BooleanExpression eventSearchFilter(String filter, String keyword) {
        log.info("filter!! : " + filter);
        log.info("keyword!! : " + keyword);

        if (StringUtils.hasText(filter)) {
            return switch (filter) {
                case "title" -> event.title.contains(keyword);
                case "username" -> event.user.nickname.contains(keyword);
                // TODO : 필요한 다른 필터 조건 추가 예정
                default -> null;
            };
        } else {
            return null;
        }
    }
}
