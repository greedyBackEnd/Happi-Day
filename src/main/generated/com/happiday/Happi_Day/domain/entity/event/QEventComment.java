package com.happiday.Happi_Day.domain.entity.event;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventComment is a Querydsl query type for EventComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventComment extends EntityPathBase<EventComment> {

    private static final long serialVersionUID = -1647421072L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventComment eventComment = new QEventComment("eventComment");

    public final com.happiday.Happi_Day.domain.entity.QBaseEntity _super = new com.happiday.Happi_Day.domain.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final QEvent event;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.happiday.Happi_Day.domain.entity.user.QUser user;

    public QEventComment(String variable) {
        this(EventComment.class, forVariable(variable), INITS);
    }

    public QEventComment(Path<? extends EventComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventComment(PathMetadata metadata, PathInits inits) {
        this(EventComment.class, metadata, inits);
    }

    public QEventComment(Class<? extends EventComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new QEvent(forProperty("event"), inits.get("event")) : null;
        this.user = inits.isInitialized("user") ? new com.happiday.Happi_Day.domain.entity.user.QUser(forProperty("user")) : null;
    }

}

