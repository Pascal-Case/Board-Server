package com.jyang.boardserver.service.impl;

import com.jyang.boardserver.dto.CommentDTO;
import com.jyang.boardserver.dto.PostDTO;
import com.jyang.boardserver.dto.TagDTO;
import com.jyang.boardserver.dto.UserDTO;
import com.jyang.boardserver.mapper.CommentMapper;
import com.jyang.boardserver.mapper.PostMapper;
import com.jyang.boardserver.mapper.TagMapper;
import com.jyang.boardserver.mapper.UserProfileMapper;
import com.jyang.boardserver.service.PostService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final UserProfileMapper userProfileMapper;
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;

    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(id);

        if (memberInfo == null) {
            log.error("register ERROR! {}", postDTO);
            throw new RuntimeException("register ERROR! 게시글 등록 메서드를 확인해주세요 " + postDTO);
        }

        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());
        postDTO.setUpdateTime(postDTO.getCreateTime());

        postMapper.register(postDTO);
        Integer postId = postDTO.getId();
        for (int i = 0; i < postDTO.getTagDTOList().size(); i++) {
            TagDTO tagDTO = postDTO.getTagDTOList().get(i);
            tagMapper.register(tagDTO);
            Integer tagId = tagDTO.getId();
            tagMapper.createPostTag(tagId, postId);
        }

    }

    @Override
    public List<PostDTO> getMyPosts(int userId) {
        System.out.println(userId);
        return postMapper.getMyPosts(userId);
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        if (postDTO == null || postDTO.getId() == 0) {
            log.error("update ERROR! {}", postDTO);
            throw new RuntimeException("update ERROR! 게시클 수정 메서드를 확인해주세요" + postDTO);
        }
        postMapper.updatePosts(postDTO);
    }

    @Override
    public void deletePosts(int userId, int postId) {
        if (userId == 0 || postId == 0) {
            log.error("delete ERROR! {}", postId);
            throw new RuntimeException("delete ERROR! 게시글 삭제 메서드를 확인해주세요 " + postId);
        }

        postMapper.deletePosts(postId);
    }

    public void registerComment(CommentDTO commentDTO) {
        if (commentDTO.getPostId() == 0) {
            log.error("registerComment ERROR! {}", commentDTO);
            throw new RuntimeException("registerComment " + commentDTO);
        }
        commentMapper.register(commentDTO);
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if (commentDTO == null) {
            log.error("updateComment ERROR!");
            throw new RuntimeException("updateComment");
        }
        commentMapper.updateComment(commentDTO);
    }

    @Override
    public void deletePostComment(int userId, int commentId) {
        if (userId == 0 || commentId == 0) {
            log.error("deletePostComment ERROR! {}", commentId);
            throw new RuntimeException("deletePostComment " + commentId);

        }
        commentMapper.deleteComment(commentId);
    }

    @Override
    public void registerTag(TagDTO tagDTO) {
        if (tagDTO == null) {
            log.error("registerTag ERROR!");
            throw new RuntimeException("registerTag");
        }
        tagMapper.register(tagDTO);
    }

    @Override
    public void updateTags(TagDTO tagDTO) {
        if (tagDTO == null) {
            log.error("updateTags ERROR!");
            throw new RuntimeException("updateTags");
        }
        tagMapper.updateTags(tagDTO);
    }

    @Override
    public void deletePostTag(int userId, int tagId) {
        if (userId == 0 || tagId == 0) {
            log.error("deletePostTag ERROR! {}", tagId);
            throw new RuntimeException("deletePostTag " + tagId);
        }
        tagMapper.deletePostTag(tagId);
    }
}
