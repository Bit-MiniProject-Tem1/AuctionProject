package com.bit.auction.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFaqAttachedFile is a Querydsl query type for FaqAttachedFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFaqAttachedFile extends EntityPathBase<FaqAttachedFile> {

    private static final long serialVersionUID = -1851486251L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFaqAttachedFile faqAttachedFile = new QFaqAttachedFile("faqAttachedFile");

    public final QFaq faq;

    public final NumberPath<Long> fileId = createNumber("fileId", Long.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath filePath = createString("filePath");

    public final StringPath fileType = createString("fileType");

    public QFaqAttachedFile(String variable) {
        this(FaqAttachedFile.class, forVariable(variable), INITS);
    }

    public QFaqAttachedFile(Path<? extends FaqAttachedFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFaqAttachedFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFaqAttachedFile(PathMetadata metadata, PathInits inits) {
        this(FaqAttachedFile.class, metadata, inits);
    }

    public QFaqAttachedFile(Class<? extends FaqAttachedFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.faq = inits.isInitialized("faq") ? new QFaq(forProperty("faq")) : null;
    }

}

