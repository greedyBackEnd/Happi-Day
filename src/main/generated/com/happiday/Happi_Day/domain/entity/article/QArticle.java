package com.happiday.Happi_Day.domain.entity.article;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = -1288254681L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticle article = new QArticle("article");

    public final com.happiday.Happi_Day.domain.entity.QBaseEntity _super = new com.happiday.Happi_Day.domain.entity.QBaseEntity(this);

    public final ListPath<ArticleComment, QArticleComment> articleComments = this.<ArticleComment, QArticleComment>createList("articleComments", ArticleComment.class, QArticleComment.class, PathInits.DIRECT2);

    public final ListPath<ArticleHashtag, QArticleHashtag> articleHashtags = this.<ArticleHashtag, QArticleHashtag>createList("articleHashtags", ArticleHashtag.class, QArticleHashtag.class, PathInits.DIRECT2);

    public final ListPath<ArticleLike, QArticleLike> articleLikes = this.<ArticleLike, QArticleLike>createList("articleLikes", ArticleLike.class, QArticleLike.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.artist.Artist, com.happiday.Happi_Day.domain.entity.artist.QArtist> artists = this.<com.happiday.Happi_Day.domain.entity.artist.Artist, com.happiday.Happi_Day.domain.entity.artist.QArtist>createList("artists", com.happiday.Happi_Day.domain.entity.artist.Artist.class, com.happiday.Happi_Day.domain.entity.artist.QArtist.class, PathInits.DIRECT2);

    public final com.happiday.Happi_Day.domain.entity.board.QBoardCategory category;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath ectArtists = createString("ectArtists");

    public final StringPath ectTeams = createString("ectTeams");

    public final StringPath eventAddress = createString("eventAddress");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> imageUrl = this.<String, StringPath>createList("imageUrl", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.team.Team, com.happiday.Happi_Day.domain.entity.team.QTeam> teams = this.<com.happiday.Happi_Day.domain.entity.team.Team, com.happiday.Happi_Day.domain.entity.team.QTeam>createList("teams", com.happiday.Happi_Day.domain.entity.team.Team.class, com.happiday.Happi_Day.domain.entity.team.QTeam.class, PathInits.DIRECT2);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.happiday.Happi_Day.domain.entity.user.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QArticle(String variable) {
        this(Article.class, forVariable(variable), INITS);
    }

    public QArticle(Path<? extends Article> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticle(PathMetadata metadata, PathInits inits) {
        this(Article.class, metadata, inits);
    }

    public QArticle(Class<? extends Article> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.happiday.Happi_Day.domain.entity.board.QBoardCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new com.happiday.Happi_Day.domain.entity.user.QUser(forProperty("user")) : null;
    }

}

