package com.bit.auction.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiry is a Querydsl query type for Inquiry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiry extends EntityPathBase<Inquiry> {

    private static final long serialVersionUID = 1797752102L;

    public static final QInquiry inquiry = new QInquiry("inquiry");

    public final StringPath inquiryAnswer = createString("inquiryAnswer");

    public final NumberPath<Integer> inquiryCnt = createNumber("inquiryCnt", Integer.class);

    public final StringPath inquiryContent = createString("inquiryContent");

    public final ListPath<InquiryFile, QInquiryFile> inquiryFileList = this.<InquiryFile, QInquiryFile>createList("inquiryFileList", InquiryFile.class, QInquiryFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> inquiryNo = createNumber("inquiryNo", Long.class);

    public final DateTimePath<java.time.LocalDateTime> inquiryRegdate = createDateTime("inquiryRegdate", java.time.LocalDateTime.class);

    public final StringPath inquiryTitle = createString("inquiryTitle");

    public final StringPath inquiryType = createString("inquiryType");

    public final StringPath inquiryWriter = createString("inquiryWriter");

    public QInquiry(String variable) {
        super(Inquiry.class, forVariable(variable));
    }

    public QInquiry(Path<? extends Inquiry> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInquiry(PathMetadata metadata) {
        super(Inquiry.class, metadata);
    }

}

