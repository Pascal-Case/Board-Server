package com.jyang.boardserver.service.impl;

import com.jyang.boardserver.dto.PostDTO;
import com.jyang.boardserver.dto.request.PostSearchRequest;
import com.jyang.boardserver.mapper.PostSearchMapper;
import com.jyang.boardserver.service.PostSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper postSearchMapper;

    @Cacheable(value = "getPosts", key = "'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            log.error("selectPosts ERROR! {}", postSearchRequest);
        }
        return postDTOList;
    }

    public List<PostDTO> getPostByTag(String tagName) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.getPostsByTag(tagName);
        } catch (RuntimeException e) {
            log.error("getPostByTag ERROR! {}", tagName);
        }
        return postDTOList;
    }
}
