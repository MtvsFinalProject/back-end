package com.project.final_project.myclassroom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyClassroom is a Querydsl query type for MyClassroom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyClassroom extends EntityPathBase<MyClassroom> {

    private static final long serialVersionUID = -1599280572L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyClassroom myClassroom = new QMyClassroom("myClassroom");

    public final NumberPath<Integer> alpha = createNumber("alpha", Integer.class);

    public final StringPath backgroundColor = createString("backgroundColor");

    public final ListPath<com.project.final_project.furniture.domain.Furniture, com.project.final_project.furniture.domain.QFurniture> furnitureList = this.<com.project.final_project.furniture.domain.Furniture, com.project.final_project.furniture.domain.QFurniture>createList("furnitureList", com.project.final_project.furniture.domain.Furniture.class, com.project.final_project.furniture.domain.QFurniture.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath previewImageUrl = createString("previewImageUrl");

    public final com.project.final_project.user.domain.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QMyClassroom(String variable) {
        this(MyClassroom.class, forVariable(variable), INITS);
    }

    public QMyClassroom(Path<? extends MyClassroom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyClassroom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyClassroom(PathMetadata metadata, PathInits inits) {
        this(MyClassroom.class, metadata, inits);
    }

    public QMyClassroom(Class<? extends MyClassroom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.project.final_project.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

