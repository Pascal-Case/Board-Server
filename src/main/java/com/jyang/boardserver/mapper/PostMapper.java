package com.jyang.boardserver.mapper;

import com.jyang.boardserver.dto.PostDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    int register(PostDTO postDTO);

    List<PostDTO> getMyPosts(int accountId);

    void updatePosts(PostDTO postDTO);

    void deletePosts(int postId);
}
