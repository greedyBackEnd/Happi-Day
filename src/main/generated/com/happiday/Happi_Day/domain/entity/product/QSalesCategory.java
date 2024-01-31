package com.happiday.Happi_Day.domain.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSalesCategory is a Querydsl query type for SalesCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalesCategory extends EntityPathBase<SalesCategory> {

    private static final long serialVersionUID = -643200044L;

    public static final QSalesCategory salesCategory = new QSalesCategory("salesCategory");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<Sales, QSales> salesList = this.<Sales, QSales>createList("salesList", Sales.class, QSales.class, PathInits.DIRECT2);

    public QSalesCategory(String variable) {
        super(SalesCategory.class, forVariable(variable));
    }

    public QSalesCategory(Path<? extends SalesCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalesCategory(PathMetadata metadata) {
        super(SalesCategory.class, metadata);
    }

}

