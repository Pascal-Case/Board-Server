package com.jyang.boardserver.dto;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private int id;
    private String name;
    private int isAdmin;
    private String contents;
    private Date createTime;
    private int views;
    private int categoryId;
    private int userId;
    private int fileId;
    private Date updateTime;
    private List<TagDTO> tagDTOList;
}
