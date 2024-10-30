package com.jyang.boardserver.dto.response;

import com.jyang.boardserver.dto.PostDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {
    private List<PostDTO> postDTOList;
}