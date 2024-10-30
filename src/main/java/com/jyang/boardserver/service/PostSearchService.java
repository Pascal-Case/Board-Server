package com.jyang.boardserver.service;

import com.jyang.boardserver.dto.PostDTO;
import com.jyang.boardserver.dto.request.PostSearchRequest;
import java.util.List;

public interface PostSearchService {
    List<PostDTO> getPosts(PostSearchRequest postSearchRequest);
}
