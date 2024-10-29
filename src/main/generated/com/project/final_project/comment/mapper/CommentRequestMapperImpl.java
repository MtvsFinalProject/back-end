package com.project.final_project.comment.mapper;

import com.project.final_project.comment.domain.Comment;
import com.project.final_project.comment.dto.CommentRequestDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-29T10:23:30+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CommentRequestMapperImpl implements CommentRequestMapper {

    @Override
    public CommentRequestDTO toDTO(Comment e) {
        if ( e == null ) {
            return null;
        }

        String content = null;

        CommentRequestDTO commentRequestDTO = new CommentRequestDTO( content );

        return commentRequestDTO;
    }

    @Override
    public Comment toEntity(CommentRequestDTO d) {
        if ( d == null ) {
            return null;
        }

        String content = null;

        Comment comment = new Comment( content );

        return comment;
    }

    @Override
    public List<CommentRequestDTO> toDTOList(List<Comment> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CommentRequestDTO> list = new ArrayList<CommentRequestDTO>( entityList.size() );
        for ( Comment comment : entityList ) {
            list.add( toDTO( comment ) );
        }

        return list;
    }

    @Override
    public List<Comment> toEntityList(List<CommentRequestDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Comment> list = new ArrayList<Comment>( dtoList.size() );
        for ( CommentRequestDTO commentRequestDTO : dtoList ) {
            list.add( toEntity( commentRequestDTO ) );
        }

        return list;
    }
}
