package com.bit.auction.goods.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeCnt is a Querydsl query type for LikeCnt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeCnt extends EntityPathBase<LikeCnt> {

    private static final long serialVersionUID = -488686696L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeCnt likeCnt = new QLikeCnt("likeCnt");

    public final QAuction auction;

    public final com.bit.auction.user.entity.QUser user;

    public QLikeCnt(String variable) {
        this(LikeCnt.class, forVariable(variable), INITS);
    }

    public QLikeCnt(Path<? extends LikeCnt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeCnt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeCnt(PathMetadata metadata, PathInits inits) {
        this(LikeCnt.class, metadata, inits);
    }

    public QLikeCnt(Class<? extends LikeCnt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auction = inits.isInitialized("auction") ? new QAuction(forProperty("auction"), inits.get("auction")) : null;
        this.user = inits.isInitialized("user") ? new com.bit.auction.user.entity.QUser(forProperty("user")) : null;
    }

}

