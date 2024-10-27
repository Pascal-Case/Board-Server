package com.jyang.boardserver.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteId {
    @NonNull
    private String userId;
    @NonNull
    private String password;
}
