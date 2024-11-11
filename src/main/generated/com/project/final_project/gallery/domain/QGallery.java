package com.project.final_project.gallery.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGallery is a Querydsl query type for Gallery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGallery extends EntityPathBase<Gallery> {

    private static final long serialVersionUID = 1375389060L;

    public static final QGallery gallery = new QGallery("gallery");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath imageBase64 = createString("imageBase64");

    public final NumberPath<Integer> likeN = createNumber("likeN", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> visitN = createNumber("visitN", Integer.class);

    public QGallery(String variable) {
        super(Gallery.class, forVariable(variable));
    }

    public QGallery(Path<? extends Gallery> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGallery(PathMetadata metadata) {
        super(Gallery.class, metadata);
    }

}

