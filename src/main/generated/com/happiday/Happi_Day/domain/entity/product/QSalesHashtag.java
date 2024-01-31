package com.happiday.Happi_Day.domain.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSalesHashtag is a Querydsl query type for SalesHashtag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalesHashtag extends EntityPathBase<SalesHashtag> {

    private static final long serialVersionUID = 1645001270L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSalesHashtag salesHashtag = new QSalesHashtag("salesHashtag");

    public final com.happiday.Happi_Day.domain.entity.article.QHashtag hashtag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSales sales;

    public QSalesHashtag(String variable) {
        this(SalesHashtag.class, forVariable(variable), INITS);
    }

    public QSalesHashtag(Path<? extends SalesHashtag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSalesHashtag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSalesHashtag(PathMetadata metadata, PathInits inits) {
        this(SalesHashtag.class, metadata, inits);
    }

    public QSalesHashtag(Class<? extends SalesHashtag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashtag = inits.isInitialized("hashtag") ? new com.happiday.Happi_Day.domain.entity.article.QHashtag(forProperty("hashtag")) : null;
        this.sales = inits.isInitialized("sales") ? new QSales(forProperty("sales"), inits.get("sales")) : null;
    }

}

