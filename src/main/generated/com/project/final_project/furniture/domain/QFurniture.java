package com.project.final_project.furniture.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFurniture is a Querydsl query type for Furniture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFurniture extends EntityPathBase<Furniture> {

    private static final long serialVersionUID = 1877736708L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFurniture furniture = new QFurniture("furniture");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> mapId = createNumber("mapId", Integer.class);

    public final com.project.final_project.myclassroom.domain.QMyClassroom myClassroom;

    public final NumberPath<Integer> objId = createNumber("objId", Integer.class);

    public final NumberPath<Integer> rot = createNumber("rot", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final NumberPath<Integer> x = createNumber("x", Integer.class);

    public final NumberPath<Integer> y = createNumber("y", Integer.class);

    public QFurniture(String variable) {
        this(Furniture.class, forVariable(variable), INITS);
    }

    public QFurniture(Path<? extends Furniture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFurniture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFurniture(PathMetadata metadata, PathInits inits) {
        this(Furniture.class, metadata, inits);
    }

    public QFurniture(Class<? extends Furniture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.myClassroom = inits.isInitialized("myClassroom") ? new com.project.final_project.myclassroom.domain.QMyClassroom(forProperty("myClassroom"), inits.get("myClassroom")) : null;
    }

}

