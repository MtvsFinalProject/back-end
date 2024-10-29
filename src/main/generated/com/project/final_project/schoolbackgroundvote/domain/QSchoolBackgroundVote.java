package com.project.final_project.schoolbackgroundvote.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSchoolBackgroundVote is a Querydsl query type for SchoolBackgroundVote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchoolBackgroundVote extends EntityPathBase<SchoolBackgroundVote> {

    private static final long serialVersionUID = 990885588L;

    public static final QSchoolBackgroundVote schoolBackgroundVote = new QSchoolBackgroundVote("schoolBackgroundVote");

    public final NumberPath<Integer> backgroundColorId = createNumber("backgroundColorId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QSchoolBackgroundVote(String variable) {
        super(SchoolBackgroundVote.class, forVariable(variable));
    }

    public QSchoolBackgroundVote(Path<? extends SchoolBackgroundVote> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchoolBackgroundVote(PathMetadata metadata) {
        super(SchoolBackgroundVote.class, metadata);
    }

}

