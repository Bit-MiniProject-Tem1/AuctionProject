package com.bit.auction.goods.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBidding is a Querydsl query type for Bidding
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBidding extends EntityPathBase<Bidding> {

    private static final long serialVersionUID = -780246847L;

    public static final QBidding bidding = new QBidding("bidding");

    public final NumberPath<Long> auctionId = createNumber("auctionId", Long.class);

    public final NumberPath<Long> bidderId = createNumber("bidderId", Long.class);

    public final NumberPath<Integer> biddingPrice = createNumber("biddingPrice", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final BooleanPath status = createBoolean("status");

    public final StringPath userId = createString("userId");

    public QBidding(String variable) {
        super(Bidding.class, forVariable(variable));
    }

    public QBidding(Path<? extends Bidding> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBidding(PathMetadata metadata) {
        super(Bidding.class, metadata);
    }

}

