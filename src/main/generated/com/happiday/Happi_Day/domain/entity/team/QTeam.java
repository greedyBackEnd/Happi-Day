package com.happiday.Happi_Day.domain.entity.team;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeam is a Querydsl query type for Team
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeam extends EntityPathBase<Team> {

    private static final long serialVersionUID = 896330103L;

    public static final QTeam team = new QTeam("team");

    public final com.happiday.Happi_Day.domain.entity.QBaseEntity _super = new com.happiday.Happi_Day.domain.entity.QBaseEntity(this);

    public final ListPath<com.happiday.Happi_Day.domain.entity.article.Article, com.happiday.Happi_Day.domain.entity.article.QArticle> articles = this.<com.happiday.Happi_Day.domain.entity.article.Article, com.happiday.Happi_Day.domain.entity.article.QArticle>createList("articles", com.happiday.Happi_Day.domain.entity.article.Article.class, com.happiday.Happi_Day.domain.entity.article.QArticle.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.artist.Artist, com.happiday.Happi_Day.domain.entity.artist.QArtist> artists = this.<com.happiday.Happi_Day.domain.entity.artist.Artist, com.happiday.Happi_Day.domain.entity.artist.QArtist>createList("artists", com.happiday.Happi_Day.domain.entity.artist.Artist.class, com.happiday.Happi_Day.domain.entity.artist.QArtist.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath description = createString("description");

    public final ListPath<com.happiday.Happi_Day.domain.entity.event.Event, com.happiday.Happi_Day.domain.entity.event.QEvent> events = this.<com.happiday.Happi_Day.domain.entity.event.Event, com.happiday.Happi_Day.domain.entity.event.QEvent>createList("events", com.happiday.Happi_Day.domain.entity.event.Event.class, com.happiday.Happi_Day.domain.entity.event.QEvent.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath logoUrl = createString("logoUrl");

    public final StringPath name = createString("name");

    public final ListPath<com.happiday.Happi_Day.domain.entity.product.Sales, com.happiday.Happi_Day.domain.entity.product.QSales> salesList = this.<com.happiday.Happi_Day.domain.entity.product.Sales, com.happiday.Happi_Day.domain.entity.product.QSales>createList("salesList", com.happiday.Happi_Day.domain.entity.product.Sales.class, com.happiday.Happi_Day.domain.entity.product.QSales.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.user.User, com.happiday.Happi_Day.domain.entity.user.QUser> subscribers = this.<com.happiday.Happi_Day.domain.entity.user.User, com.happiday.Happi_Day.domain.entity.user.QUser>createList("subscribers", com.happiday.Happi_Day.domain.entity.user.User.class, com.happiday.Happi_Day.domain.entity.user.QUser.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTeam(String variable) {
        super(Team.class, forVariable(variable));
    }

    public QTeam(Path<? extends Team> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeam(PathMetadata metadata) {
        super(Team.class, metadata);
    }

}

