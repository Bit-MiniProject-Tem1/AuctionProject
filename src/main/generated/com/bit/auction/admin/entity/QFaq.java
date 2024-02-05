package com.bit.auction.admin.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFaq is a Querydsl query type for Faq
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFaq extends EntityPathBase<Faq> {

    private static final long serialVersionUID = 1187006915L;

    public static final QFaq faq = new QFaq("faq");

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final ListPath<FaqAttachedFile, QFaqAttachedFile> faqAttachedFileList = this.<FaqAttachedFile, QFaqAttachedFile>createList("faqAttachedFileList", FaqAttachedFile.class, QFaqAttachedFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> faqId = createNumber("faqId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regdate = createDateTime("regdate", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewsCount = createNumber("viewsCount", Integer.class);

    public QFaq(String variable) {
        super(Faq.class, forVariable(variable));
    }

    public QFaq(Path<? extends Faq> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFaq(PathMetadata metadata) {
        super(Faq.class, metadata);
    }

}

