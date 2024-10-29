package com.project.final_project.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchool is a Querydsl query type for School
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchool extends EntityPathBase<School> {

    private static final long serialVersionUID = 1974782564L;

    public static final QSchool school = new QSchool("school");

    public final NumberPath<Integer> backgroundColorId = createNumber("backgroundColorId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath schoolName = createString("schoolName");

    public final ListPath<com.project.final_project.user.domain.User, com.project.final_project.user.domain.QUser> userList = this.<com.project.final_project.user.domain.User, com.project.final_project.user.domain.QUser>createList("userList", com.project.final_project.user.domain.User.class, com.project.final_project.user.domain.QUser.class, PathInits.DIRECT2);

    public QSchool(String variable) {
        super(School.class, forVariable(variable));
    }

    public QSchool(Path<? extends School> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchool(PathMetadata metadata) {
        super(School.class, metadata);
    }

}

