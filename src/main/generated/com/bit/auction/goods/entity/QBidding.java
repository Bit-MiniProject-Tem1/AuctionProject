package com.bit.auction.goods.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBidding is a Querydsl query type for Bidding
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBidding extends EntityPathBase<Bidding> {

    private static final long serialVersionUID = -780246847L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBidding bidding = new QBidding("bidding");

    public final QAuction auction;

    public final NumberPath<Long> biddingId = createNumber("biddingId", Long.class);

    public final NumberPath<Integer> biddingPrice = createNumber("biddingPrice", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final NumberPath<Integer> payment = createNumber("payment", Integer.class);

    public final BooleanPath status = createBoolean("status");

    public final StringPath userId = createString("userId");

    public QBidding(String variable) {
        this(Bidding.class, forVariable(variable), INITS);
    }

    public QBidding(Path<? extends Bidding> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBidding(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBidding(PathMetadata metadata, PathInits inits) {
        this(Bidding.class, metadata, inits);
    }

    public QBidding(Class<? extends Bidding> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auction = inits.isInitialized("auction") ? new QAuction(forProperty("auction"), inits.get("auction")) : null;
    }

}

