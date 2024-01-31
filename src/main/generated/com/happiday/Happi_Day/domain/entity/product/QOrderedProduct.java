package com.happiday.Happi_Day.domain.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderedProduct is a Querydsl query type for OrderedProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderedProduct extends EntityPathBase<OrderedProduct> {

    private static final long serialVersionUID = 132271960L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderedProduct orderedProduct = new QOrderedProduct("orderedProduct");

    public final com.happiday.Happi_Day.domain.entity.QBaseEntity _super = new com.happiday.Happi_Day.domain.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOrder order;

    public final QProduct product;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOrderedProduct(String variable) {
        this(OrderedProduct.class, forVariable(variable), INITS);
    }

    public QOrderedProduct(Path<? extends OrderedProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderedProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderedProduct(PathMetadata metadata, PathInits inits) {
        this(OrderedProduct.class, metadata, inits);
    }

    public QOrderedProduct(Class<? extends OrderedProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

