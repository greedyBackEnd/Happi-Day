package com.happiday.Happi_Day.domain.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSalesLike is a Querydsl query type for SalesLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalesLike extends EntityPathBase<SalesLike> {

    private static final long serialVersionUID = 1661884781L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSalesLike salesLike = new QSalesLike("salesLike");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSales sales;

    public final com.happiday.Happi_Day.domain.entity.user.QUser user;

    public QSalesLike(String variable) {
        this(SalesLike.class, forVariable(variable), INITS);
    }

    public QSalesLike(Path<? extends SalesLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSalesLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSalesLike(PathMetadata metadata, PathInits inits) {
        this(SalesLike.class, metadata, inits);
    }

    public QSalesLike(Class<? extends SalesLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sales = inits.isInitialized("sales") ? new QSales(forProperty("sales"), inits.get("sales")) : null;
        this.user = inits.isInitialized("user") ? new com.happiday.Happi_Day.domain.entity.user.QUser(forProperty("user")) : null;
    }

}

