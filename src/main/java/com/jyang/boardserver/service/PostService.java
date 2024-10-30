package com.jyang.boardserver.service;

import com.jyang.boardserver.dto.CommentDTO;
import com.jyang.boardserver.dto.PostDTO;
import com.jyang.boardserver.dto.TagDTO;
import java.util.List;

public interface PostService {
    void register(String id, PostDTO postDTO);

    List<PostDTO> getMyPosts(int accountId);

    void updatePosts(PostDTO postDTO);

    void deletePosts(int userId, int postId);

    void registerComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deletePostComment(int userId, int commentId);

    void registerTag(TagDTO tagDTO);

    void updateTags(TagDTO tagDTO);

    void deletePostTag(int userId, int tagId);
}
