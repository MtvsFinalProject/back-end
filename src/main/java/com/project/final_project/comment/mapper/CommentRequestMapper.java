package com.project.final_project.comment.mapper;

import com.project.final_project.comment.domain.Comment;
import com.project.final_project.comment.dto.CommentRequestDTO;
import com.project.final_project.common.global.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentRequestMapper extends GenericMapper<CommentRequestDTO, Comment> {

    CommentRequestMapper INSTANCE = Mappers.getMapper(CommentRequestMapper.class);

}
