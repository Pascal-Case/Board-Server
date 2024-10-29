package com.jyang.boardserver.service.impl;

import com.jyang.boardserver.dto.PostDTO;
import com.jyang.boardserver.dto.UserDTO;
import com.jyang.boardserver.mapper.PostMapper;
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
}
