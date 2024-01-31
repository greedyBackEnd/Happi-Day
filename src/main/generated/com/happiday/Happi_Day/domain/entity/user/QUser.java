package com.happiday.Happi_Day.domain.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1411620407L;

    public static final QUser user = new QUser("user");

    public final com.happiday.Happi_Day.domain.entity.QBaseEntity _super = new com.happiday.Happi_Day.domain.entity.QBaseEntity(this);

    public final ListPath<com.happiday.Happi_Day.domain.entity.article.ArticleComment, com.happiday.Happi_Day.domain.entity.article.QArticleComment> articleComments = this.<com.happiday.Happi_Day.domain.entity.article.ArticleComment, com.happiday.Happi_Day.domain.entity.article.QArticleComment>createList("articleComments", com.happiday.Happi_Day.domain.entity.article.ArticleComment.class, com.happiday.Happi_Day.domain.entity.article.QArticleComment.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.article.ArticleLike, com.happiday.Happi_Day.domain.entity.article.QArticleLike> articleLikes = this.<com.happiday.Happi_Day.domain.entity.article.ArticleLike, com.happiday.Happi_Day.domain.entity.article.QArticleLike>createList("articleLikes", com.happiday.Happi_Day.domain.entity.article.ArticleLike.class, com.happiday.Happi_Day.domain.entity.article.QArticleLike.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.article.Article, com.happiday.Happi_Day.domain.entity.article.QArticle> articles = this.<com.happiday.Happi_Day.domain.entity.article.Article, com.happiday.Happi_Day.domain.entity.article.QArticle>createList("articles", com.happiday.Happi_Day.domain.entity.article.Article.class, com.happiday.Happi_Day.domain.entity.article.QArticle.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.chat.ChatMessage, com.happiday.Happi_Day.domain.entity.chat.QChatMessage> chatMessages = this.<com.happiday.Happi_Day.domain.entity.chat.ChatMessage, com.happiday.Happi_Day.domain.entity.chat.QChatMessage>createList("chatMessages", com.happiday.Happi_Day.domain.entity.chat.ChatMessage.class, com.happiday.Happi_Day.domain.entity.chat.QChatMessage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final ListPath<com.happiday.Happi_Day.domain.entity.event.Event, com.happiday.Happi_Day.domain.entity.event.QEvent> eventComments = this.<com.happiday.Happi_Day.domain.entity.event.Event, com.happiday.Happi_Day.domain.entity.event.QEvent>createList("eventComments", com.happiday.Happi_Day.domain.entity.event.Event.class, com.happiday.Happi_Day.domain.entity.event.QEvent.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.event.EventLike, com.happiday.Happi_Day.domain.entity.event.QEventLike> eventLikes = this.<com.happiday.Happi_Day.domain.entity.event.EventLike, com.happiday.Happi_Day.domain.entity.event.QEventLike>createList("eventLikes", com.happiday.Happi_Day.domain.entity.event.EventLike.class, com.happiday.Happi_Day.domain.entity.event.QEventLike.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.event.EventParticipation, com.happiday.Happi_Day.domain.entity.event.QEventParticipation> eventParticipationList = this.<com.happiday.Happi_Day.domain.entity.event.EventParticipation, com.happiday.Happi_Day.domain.entity.event.QEventParticipation>createList("eventParticipationList", com.happiday.Happi_Day.domain.entity.event.EventParticipation.class, com.happiday.Happi_Day.domain.entity.event.QEventParticipation.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.event.EventReview, com.happiday.Happi_Day.domain.entity.event.QEventReview> eventReviews = this.<com.happiday.Happi_Day.domain.entity.event.EventReview, com.happiday.Happi_Day.domain.entity.event.QEventReview>createList("eventReviews", com.happiday.Happi_Day.domain.entity.event.EventReview.class, com.happiday.Happi_Day.domain.entity.event.QEventReview.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.event.Event, com.happiday.Happi_Day.domain.entity.event.QEvent> events = this.<com.happiday.Happi_Day.domain.entity.event.Event, com.happiday.Happi_Day.domain.entity.event.QEvent>createList("events", com.happiday.Happi_Day.domain.entity.event.Event.class, com.happiday.Happi_Day.domain.entity.event.QEvent.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final BooleanPath isActive = createBoolean("isActive");

    public final BooleanPath isTermsAgreed = createBoolean("isTermsAgreed");

    public final DateTimePath<java.time.LocalDateTime> lastLoginAt = createDateTime("lastLoginAt", java.time.LocalDateTime.class);

    public final StringPath nickname = createString("nickname");

    public final ListPath<com.happiday.Happi_Day.domain.entity.product.Order, com.happiday.Happi_Day.domain.entity.product.QOrder> orders = this.<com.happiday.Happi_Day.domain.entity.product.Order, com.happiday.Happi_Day.domain.entity.product.QOrder>createList("orders", com.happiday.Happi_Day.domain.entity.product.Order.class, com.happiday.Happi_Day.domain.entity.product.QOrder.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath realname = createString("realname");

    public final ListPath<com.happiday.Happi_Day.domain.entity.chat.ChatRoom, com.happiday.Happi_Day.domain.entity.chat.QChatRoom> receiveChatRooms = this.<com.happiday.Happi_Day.domain.entity.chat.ChatRoom, com.happiday.Happi_Day.domain.entity.chat.QChatRoom>createList("receiveChatRooms", com.happiday.Happi_Day.domain.entity.chat.ChatRoom.class, com.happiday.Happi_Day.domain.entity.chat.QChatRoom.class, PathInits.DIRECT2);

    public final EnumPath<RoleType> role = createEnum("role", RoleType.class);

    public final ListPath<com.happiday.Happi_Day.domain.entity.product.SalesLike, com.happiday.Happi_Day.domain.entity.product.QSalesLike> salesLikes = this.<com.happiday.Happi_Day.domain.entity.product.SalesLike, com.happiday.Happi_Day.domain.entity.product.QSalesLike>createList("salesLikes", com.happiday.Happi_Day.domain.entity.product.SalesLike.class, com.happiday.Happi_Day.domain.entity.product.QSalesLike.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.product.Sales, com.happiday.Happi_Day.domain.entity.product.QSales> salesList = this.<com.happiday.Happi_Day.domain.entity.product.Sales, com.happiday.Happi_Day.domain.entity.product.QSales>createList("salesList", com.happiday.Happi_Day.domain.entity.product.Sales.class, com.happiday.Happi_Day.domain.entity.product.QSales.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.chat.ChatRoom, com.happiday.Happi_Day.domain.entity.chat.QChatRoom> sendChatRooms = this.<com.happiday.Happi_Day.domain.entity.chat.ChatRoom, com.happiday.Happi_Day.domain.entity.chat.QChatRoom>createList("sendChatRooms", com.happiday.Happi_Day.domain.entity.chat.ChatRoom.class, com.happiday.Happi_Day.domain.entity.chat.QChatRoom.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.artist.Artist, com.happiday.Happi_Day.domain.entity.artist.QArtist> subscribedArtists = this.<com.happiday.Happi_Day.domain.entity.artist.Artist, com.happiday.Happi_Day.domain.entity.artist.QArtist>createList("subscribedArtists", com.happiday.Happi_Day.domain.entity.artist.Artist.class, com.happiday.Happi_Day.domain.entity.artist.QArtist.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.team.Team, com.happiday.Happi_Day.domain.entity.team.QTeam> subscribedTeams = this.<com.happiday.Happi_Day.domain.entity.team.Team, com.happiday.Happi_Day.domain.entity.team.QTeam>createList("subscribedTeams", com.happiday.Happi_Day.domain.entity.team.Team.class, com.happiday.Happi_Day.domain.entity.team.QTeam.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> termsAt = createDateTime("termsAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

