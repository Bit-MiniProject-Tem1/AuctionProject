package com.bit.auction.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiryFile is a Querydsl query type for InquiryFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiryFile extends EntityPathBase<InquiryFile> {

    private static final long serialVersionUID = -736760894L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquiryFile inquiryFile = new QInquiryFile("inquiryFile");

    public final QInquiry inquiry;

    public final StringPath inquiryFileCate = createString("inquiryFileCate");

    public final StringPath inquiryFileName = createString("inquiryFileName");

    public final NumberPath<Long> inquiryFileNo = createNumber("inquiryFileNo", Long.class);

    public final StringPath inquiryFileOrigin = createString("inquiryFileOrigin");

    public final StringPath inquiryFilePath = createString("inquiryFilePath");

    public QInquiryFile(String variable) {
        this(InquiryFile.class, forVariable(variable), INITS);
    }

    public QInquiryFile(Path<? extends InquiryFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquiryFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquiryFile(PathMetadata metadata, PathInits inits) {
        this(InquiryFile.class, metadata, inits);
    }

    public QInquiryFile(Class<? extends InquiryFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inquiry = inits.isInitialized("inquiry") ? new QInquiry(forProperty("inquiry")) : null;
    }

}

