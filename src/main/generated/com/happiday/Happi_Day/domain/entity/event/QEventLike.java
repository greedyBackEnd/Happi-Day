package com.happiday.Happi_Day.domain.entity.event;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventLike is a Querydsl query type for EventLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventLike extends EntityPathBase<EventLike> {

    private static final long serialVersionUID = -1600944602L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventLike eventLike = new QEventLike("eventLike");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QEvent event;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.happiday.Happi_Day.domain.entity.user.QUser user;

    public QEventLike(String variable) {
        this(EventLike.class, forVariable(variable), INITS);
    }

    public QEventLike(Path<? extends EventLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventLike(PathMetadata metadata, PathInits inits) {
        this(EventLike.class, metadata, inits);
    }

    public QEventLike(Class<? extends EventLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new QEvent(forProperty("event"), inits.get("event")) : null;
        this.user = inits.isInitialized("user") ? new com.happiday.Happi_Day.domain.entity.user.QUser(forProperty("user")) : null;
    }

}

