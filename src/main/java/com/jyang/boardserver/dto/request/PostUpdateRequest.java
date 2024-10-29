package com.jyang.boardserver.dto.request;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequest {
    private String name;
    private String contents;
    private int views;
    private int categoryId;
    private int userId;
    private int fileId;
    private Date updateTime;
}
