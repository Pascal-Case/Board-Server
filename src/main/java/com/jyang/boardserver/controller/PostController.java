package com.jyang.boardserver.controller;

import static com.jyang.boardserver.aop.LoginCheck.UserType.USER;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.jyang.boardserver.aop.LoginCheck;
import com.jyang.boardserver.dto.PostDTO;
import com.jyang.boardserver.dto.UserDTO;
import com.jyang.boardserver.dto.request.PostDeleteRequest;
import com.jyang.boardserver.dto.request.PostUpdateRequest;
import com.jyang.boardserver.dto.response.CommonResponse;
import com.jyang.boardserver.service.impl.PostServiceImpl;
import com.jyang.boardserver.service.impl.UserServiceImpl;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    @PostMapping
    @LoginCheck(type = USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPost(String accountId, @RequestBody PostDTO postDTO) {

        postService.register(accountId, postDTO);
        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(CREATED, "SUCCESS", "registerPost", postDTO);
        return ResponseEntity.status(CREATED).body(commonResponse);
    }

    @GetMapping("my-posts")
    @LoginCheck(type = USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> myPostInfo(String accountId) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        List<PostDTO> postDTOList = postService.getMyPosts(memberInfo.getId());
        CommonResponse<List<PostDTO>> commonResponse = new CommonResponse<>(OK, "SUCCESS", "myPostInfo", postDTOList);
        return ResponseEntity.status(OK).body(commonResponse);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = USER)
    public ResponseEntity<CommonResponse<PostDTO>> updatePosts(String accountId,
                                                               @PathVariable(name = "postId") int postId,
                                                               @RequestBody PostUpdateRequest postUpdateRequest) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postUpdateRequest.getName())
                .contents(postUpdateRequest.getContents())
                .views(postUpdateRequest.getViews())
                .categoryId(postUpdateRequest.getCategoryId())
                .userId(memberInfo.getId())
                .fileId(postUpdateRequest.getFileId())
                .updateTime(new Date())
                .build();
        System.out.println(postDTO);
        postService.updatePosts(postDTO);
        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(OK, "SUCCESS", "updatePosts", postDTO);
        return ResponseEntity.status(OK).body(commonResponse);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deletePosts(String accountId,
                                                                         @PathVariable(name = "postId") int postId,
                                                                         @RequestBody
                                                                         PostDeleteRequest postDeleteRequest) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        postService.deletePosts(memberInfo.getId(), postId);
        CommonResponse<PostDeleteRequest> commonResponse = new CommonResponse<>(OK, "SUCCESS", "deletePosts",
                postDeleteRequest);
        return ResponseEntity.status(OK).body(commonResponse);
    }

}
