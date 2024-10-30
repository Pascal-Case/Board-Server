package com.jyang.boardserver.mapper;

import com.jyang.boardserver.dto.PostDTO;
import com.jyang.boardserver.dto.request.PostSearchRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostSearchMapper {
    List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);

    List<PostDTO> getPostsByTag(String tagName);
}
