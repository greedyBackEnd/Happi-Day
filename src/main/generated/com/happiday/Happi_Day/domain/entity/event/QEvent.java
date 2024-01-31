package com.happiday.Happi_Day.domain.entity.event;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = -89656849L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvent event = new QEvent("event");

    public final com.happiday.Happi_Day.domain.entity.QBaseEntity _super = new com.happiday.Happi_Day.domain.entity.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final ListPath<com.happiday.Happi_Day.domain.entity.artist.Artist, com.happiday.Happi_Day.domain.entity.artist.QArtist> artists = this.<com.happiday.Happi_Day.domain.entity.artist.Artist, com.happiday.Happi_Day.domain.entity.artist.QArtist>createList("artists", com.happiday.Happi_Day.domain.entity.artist.Artist.class, com.happiday.Happi_Day.domain.entity.artist.QArtist.class, PathInits.DIRECT2);

    public final ListPath<EventComment, QEventComment> comments = this.<EventComment, QEventComment>createList("comments", EventComment.class, QEventComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final ListPath<EventHashtag, QEventHashtag> eventHashtags = this.<EventHashtag, QEventHashtag>createList("eventHashtags", EventHashtag.class, QEventHashtag.class, PathInits.DIRECT2);

    public final ListPath<EventParticipation, QEventParticipation> eventParticipationList = this.<EventParticipation, QEventParticipation>createList("eventParticipationList", EventParticipation.class, QEventParticipation.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final ListPath<EventLike, QEventLike> likes = this.<EventLike, QEventLike>createList("likes", EventLike.class, QEventLike.class, PathInits.DIRECT2);

    public final StringPath location = createString("location");

    public final ListPath<EventReview, QEventReview> reviews = this.<EventReview, QEventReview>createList("reviews", EventReview.class, QEventReview.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final ListPath<com.happiday.Happi_Day.domain.entity.team.Team, com.happiday.Happi_Day.domain.entity.team.QTeam> teams = this.<com.happiday.Happi_Day.domain.entity.team.Team, com.happiday.Happi_Day.domain.entity.team.QTeam>createList("teams", com.happiday.Happi_Day.domain.entity.team.Team.class, com.happiday.Happi_Day.domain.entity.team.QTeam.class, PathInits.DIRECT2);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.happiday.Happi_Day.domain.entity.user.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QEvent(String variable) {
        this(Event.class, forVariable(variable), INITS);
    }

    public QEvent(Path<? extends Event> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEvent(PathMetadata metadata, PathInits inits) {
        this(Event.class, metadata, inits);
    }

    public QEvent(Class<? extends Event> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.happiday.Happi_Day.domain.entity.user.QUser(forProperty("user")) : null;
    }

}

