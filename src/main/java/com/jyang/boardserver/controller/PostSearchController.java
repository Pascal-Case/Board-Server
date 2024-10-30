package com.jyang.boardserver.controller;

import com.jyang.boardserver.dto.request.PostSearchRequest;
import com.jyang.boardserver.dto.response.PostSearchResponse;
import com.jyang.boardserver.service.impl.PostSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@Slf4j
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest postSearchRequest) {
        return new PostSearchResponse(postSearchService.getPosts(postSearchRequest));
    }

    @GetMapping
    public PostSearchResponse searchByTagName(String tagName) {
        return new PostSearchResponse(postSearchService.getPostByTag(tagName));
    }
}
