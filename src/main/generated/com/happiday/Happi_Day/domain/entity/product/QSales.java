package com.happiday.Happi_Day.domain.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSales is a Querydsl query type for Sales
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSales extends EntityPathBase<Sales> {

    private static final long serialVersionUID = -1482325834L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSales sales = new QSales("sales");

    public final com.happiday.Happi_Day.domain.entity.QBaseEntity _super = new com.happiday.Happi_Day.domain.entity.QBaseEntity(this);

    public final StringPath account = createString("account");

    public final ListPath<com.happiday.Happi_Day.domain.entity.artist.Artist, com.happiday.Happi_Day.domain.entity.artist.QArtist> artists = this.<com.happiday.Happi_Day.domain.entity.artist.Artist, com.happiday.Happi_Day.domain.entity.artist.QArtist>createList("artists", com.happiday.Happi_Day.domain.entity.artist.Artist.class, com.happiday.Happi_Day.domain.entity.artist.QArtist.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final ListPath<Delivery, QDelivery> deliveries = this.<Delivery, QDelivery>createList("deliveries", Delivery.class, QDelivery.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> imageUrl = this.<String, StringPath>createList("imageUrl", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final ListPath<Order, QOrder> orders = this.<Order, QOrder>createList("orders", Order.class, QOrder.class, PathInits.DIRECT2);

    public final ListPath<Product, QProduct> products = this.<Product, QProduct>createList("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public final QSalesCategory salesCategory;

    public final ListPath<SalesHashtag, QSalesHashtag> salesHashtags = this.<SalesHashtag, QSalesHashtag>createList("salesHashtags", SalesHashtag.class, QSalesHashtag.class, PathInits.DIRECT2);

    public final ListPath<SalesLike, QSalesLike> salesLikes = this.<SalesLike, QSalesLike>createList("salesLikes", SalesLike.class, QSalesLike.class, PathInits.DIRECT2);

    public final EnumPath<SalesStatus> salesStatus = createEnum("salesStatus", SalesStatus.class);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final ListPath<com.happiday.Happi_Day.domain.entity.team.Team, com.happiday.Happi_Day.domain.entity.team.QTeam> teams = this.<com.happiday.Happi_Day.domain.entity.team.Team, com.happiday.Happi_Day.domain.entity.team.QTeam>createList("teams", com.happiday.Happi_Day.domain.entity.team.Team.class, com.happiday.Happi_Day.domain.entity.team.QTeam.class, PathInits.DIRECT2);

    public final StringPath thumbnailImage = createString("thumbnailImage");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.happiday.Happi_Day.domain.entity.user.QUser users;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QSales(String variable) {
        this(Sales.class, forVariable(variable), INITS);
    }

    public QSales(Path<? extends Sales> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSales(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSales(PathMetadata metadata, PathInits inits) {
        this(Sales.class, metadata, inits);
    }

    public QSales(Class<? extends Sales> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.salesCategory = inits.isInitialized("salesCategory") ? new QSalesCategory(forProperty("salesCategory")) : null;
        this.users = inits.isInitialized("users") ? new com.happiday.Happi_Day.domain.entity.user.QUser(forProperty("users")) : null;
    }

}

