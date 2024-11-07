package com.project.final_project.lockerguestbook.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLockerGuestBook is a Querydsl query type for LockerGuestBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLockerGuestBook extends EntityPathBase<LockerGuestBook> {

    private static final long serialVersionUID = 1624085924L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLockerGuestBook lockerGuestBook = new QLockerGuestBook("lockerGuestBook");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.project.final_project.user.domain.QUser user;

    public QLockerGuestBook(String variable) {
        this(LockerGuestBook.class, forVariable(variable), INITS);
    }

    public QLockerGuestBook(Path<? extends LockerGuestBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLockerGuestBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLockerGuestBook(PathMetadata metadata, PathInits inits) {
        this(LockerGuestBook.class, metadata, inits);
    }

    public QLockerGuestBook(Class<? extends LockerGuestBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.project.final_project.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

