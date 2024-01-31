package com.happiday.Happi_Day.domain.entity.article;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHashtag is a Querydsl query type for Hashtag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHashtag extends EntityPathBase<Hashtag> {

    private static final long serialVersionUID = 141670909L;

    public static final QHashtag hashtag = new QHashtag("hashtag");

    public final ListPath<ArticleHashtag, QArticleHashtag> articleHashtags = this.<ArticleHashtag, QArticleHashtag>createList("articleHashtags", ArticleHashtag.class, QArticleHashtag.class, PathInits.DIRECT2);

    public final ListPath<com.happiday.Happi_Day.domain.entity.event.EventHashtag, com.happiday.Happi_Day.domain.entity.event.QEventHashtag> eventHashtags = this.<com.happiday.Happi_Day.domain.entity.event.EventHashtag, com.happiday.Happi_Day.domain.entity.event.QEventHashtag>createList("eventHashtags", com.happiday.Happi_Day.domain.entity.event.EventHashtag.class, com.happiday.Happi_Day.domain.entity.event.QEventHashtag.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.happiday.Happi_Day.domain.entity.product.SalesHashtag, com.happiday.Happi_Day.domain.entity.product.QSalesHashtag> salesHashtags = this.<com.happiday.Happi_Day.domain.entity.product.SalesHashtag, com.happiday.Happi_Day.domain.entity.product.QSalesHashtag>createList("salesHashtags", com.happiday.Happi_Day.domain.entity.product.SalesHashtag.class, com.happiday.Happi_Day.domain.entity.product.QSalesHashtag.class, PathInits.DIRECT2);

    public final StringPath tag = createString("tag");

    public QHashtag(String variable) {
        super(Hashtag.class, forVariable(variable));
    }

    public QHashtag(Path<? extends Hashtag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHashtag(PathMetadata metadata) {
        super(Hashtag.class, metadata);
    }

}

