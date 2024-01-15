package com.bit.auction.goods.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuctionImg is a Querydsl query type for AuctionImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuctionImg extends EntityPathBase<AuctionImg> {

    private static final long serialVersionUID = -415364230L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuctionImg auctionImg = new QAuctionImg("auctionImg");

    public final QAuction auction;

    public final StringPath fileName = createString("fileName");

    public final StringPath fileUrl = createString("fileUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRepresentative = createBoolean("isRepresentative");

    public QAuctionImg(String variable) {
        this(AuctionImg.class, forVariable(variable), INITS);
    }

    public QAuctionImg(Path<? extends AuctionImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuctionImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuctionImg(PathMetadata metadata, PathInits inits) {
        this(AuctionImg.class, metadata, inits);
    }

    public QAuctionImg(Class<? extends AuctionImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auction = inits.isInitialized("auction") ? new QAuction(forProperty("auction"), inits.get("auction")) : null;
    }

}

