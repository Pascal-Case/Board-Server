package com.jyang.boardserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    public enum Status {
        DEFAULT, ADMIN, DELETED
    }

    private int id;
    private String userId;
    private String password;
    private String nickName;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    private Date createTime;
    @JsonProperty("isWithDraw")
    private boolean isWithDraw;
    private Status status;
    private Date updateTime;

    public boolean hasNullDataBeforeRegister(UserDTO userDTO) {
        return userDTO.getUserId() == null || userDTO.getPassword() == null || userDTO.getNickName() == null;
    }
}
