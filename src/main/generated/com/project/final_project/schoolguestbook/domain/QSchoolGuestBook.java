package com.project.final_project.schoolguestbook.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchoolGuestBook is a Querydsl query type for SchoolGuestBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchoolGuestBook extends EntityPathBase<SchoolGuestBook> {

    private static final long serialVersionUID = 740740388L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchoolGuestBook schoolGuestBook = new QSchoolGuestBook("schoolGuestBook");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath registDate = createString("registDate");

    public final StringPath rgb = createString("rgb");

    public final com.project.final_project.school.domain.QSchool school;

    public final com.project.final_project.user.domain.QUser user;

    public QSchoolGuestBook(String variable) {
        this(SchoolGuestBook.class, forVariable(variable), INITS);
    }

    public QSchoolGuestBook(Path<? extends SchoolGuestBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchoolGuestBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchoolGuestBook(PathMetadata metadata, PathInits inits) {
        this(SchoolGuestBook.class, metadata, inits);
    }

    public QSchoolGuestBook(Class<? extends SchoolGuestBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.school = inits.isInitialized("school") ? new com.project.final_project.school.domain.QSchool(forProperty("school")) : null;
        this.user = inits.isInitialized("user") ? new com.project.final_project.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

