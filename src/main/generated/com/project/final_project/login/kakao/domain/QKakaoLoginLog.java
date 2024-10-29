package com.project.final_project.login.kakao.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKakaoLoginLog is a Querydsl query type for KakaoLoginLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKakaoLoginLog extends EntityPathBase<KakaoLoginLog> {

    private static final long serialVersionUID = -414422396L;

    public static final QKakaoLoginLog kakaoLoginLog = new QKakaoLoginLog("kakaoLoginLog");

    public final StringPath accessToken = createString("accessToken");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath state = createString("state");

    public QKakaoLoginLog(String variable) {
        super(KakaoLoginLog.class, forVariable(variable));
    }

    public QKakaoLoginLog(Path<? extends KakaoLoginLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKakaoLoginLog(PathMetadata metadata) {
        super(KakaoLoginLog.class, metadata);
    }

}

