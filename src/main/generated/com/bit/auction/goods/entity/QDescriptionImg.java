package com.bit.auction.goods.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDescriptionImg is a Querydsl query type for DescriptionImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDescriptionImg extends EntityPathBase<DescriptionImg> {

    private static final long serialVersionUID = -662416863L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDescriptionImg descriptionImg = new QDescriptionImg("descriptionImg");

    public final QAuction auction;

    public final StringPath fileName = createString("fileName");

    public final StringPath fileUrl = createString("fileUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QDescriptionImg(String variable) {
        this(DescriptionImg.class, forVariable(variable), INITS);
    }

    public QDescriptionImg(Path<? extends DescriptionImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDescriptionImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDescriptionImg(PathMetadata metadata, PathInits inits) {
        this(DescriptionImg.class, metadata, inits);
    }

    public QDescriptionImg(Class<? extends DescriptionImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auction = inits.isInitialized("auction") ? new QAuction(forProperty("auction"), inits.get("auction")) : null;
    }

}

