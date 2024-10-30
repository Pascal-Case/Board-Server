package com.jyang.boardserver.dto.request;

import com.jyang.boardserver.dto.CategoryDTO.SortStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PostSearchRequest {
    private int id;
    private String name;
    private String contents;
    private int views;
    private int categoryId;
    private int userId;
    private SortStatus sortStatus;
}
