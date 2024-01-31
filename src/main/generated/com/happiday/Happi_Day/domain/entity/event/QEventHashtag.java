package com.happiday.Happi_Day.domain.entity.event;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventHashtag is a Querydsl query type for EventHashtag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventHashtag extends EntityPathBase<EventHashtag> {

    private static final long serialVersionUID = -1900271907L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventHashtag eventHashtag = new QEventHashtag("eventHashtag");

    public final QEvent event;

    public final com.happiday.Happi_Day.domain.entity.article.QHashtag hashtag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QEventHashtag(String variable) {
        this(EventHashtag.class, forVariable(variable), INITS);
    }

    public QEventHashtag(Path<? extends EventHashtag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventHashtag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventHashtag(PathMetadata metadata, PathInits inits) {
        this(EventHashtag.class, metadata, inits);
    }

    public QEventHashtag(Class<? extends EventHashtag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new QEvent(forProperty("event"), inits.get("event")) : null;
        this.hashtag = inits.isInitialized("hashtag") ? new com.happiday.Happi_Day.domain.entity.article.QHashtag(forProperty("hashtag")) : null;
    }

}

