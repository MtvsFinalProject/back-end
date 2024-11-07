package com.project.final_project.board.friendrecruitboard.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFriendRecruitBoard is a Querydsl query type for FriendRecruitBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriendRecruitBoard extends EntityPathBase<FriendRecruitBoard> {

    private static final long serialVersionUID = 1529716496L;

    public static final QFriendRecruitBoard friendRecruitBoard = new QFriendRecruitBoard("friendRecruitBoard");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QFriendRecruitBoard(String variable) {
        super(FriendRecruitBoard.class, forVariable(variable));
    }

    public QFriendRecruitBoard(Path<? extends FriendRecruitBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFriendRecruitBoard(PathMetadata metadata) {
        super(FriendRecruitBoard.class, metadata);
    }

}

