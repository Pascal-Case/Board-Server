package com.jyang.boardserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private HttpStatus status;
    private String code;
    private String message;
    private T requestBody;
}
