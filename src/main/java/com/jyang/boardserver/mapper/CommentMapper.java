package com.jyang.boardserver.mapper;

import com.jyang.boardserver.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    int register(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deleteComment(int commentId);
}
