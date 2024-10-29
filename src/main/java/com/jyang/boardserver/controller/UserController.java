package com.jyang.boardserver.controller;

import static com.jyang.boardserver.dto.UserDTO.Status.ADMIN;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.jyang.boardserver.aop.LoginCheck;
import com.jyang.boardserver.aop.LoginCheck.UserType;
import com.jyang.boardserver.dto.UserDTO;
import com.jyang.boardserver.dto.request.LoginRequest;
import com.jyang.boardserver.dto.request.UserDeleteId;
import com.jyang.boardserver.dto.request.UserUpdatePasswordRequest;
import com.jyang.boardserver.dto.response.LoginResponse;
import com.jyang.boardserver.dto.response.UserInfoResponse;
import com.jyang.boardserver.service.impl.UserServiceImpl;
import com.jyang.boardserver.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("sign-up")
    @ResponseStatus(CREATED)
    public void signUp(@RequestBody UserDTO userDTO) {
        if (userDTO.hasNullDataBeforeRegister(userDTO)) {
            throw new RuntimeException("회원가입 정보를 확인해 주세요");
        }
        userService.register(userDTO);
    }

    @PostMapping("sign-in")
    public HttpStatus login(@RequestBody LoginRequest userLoginRequest, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();
        LoginResponse loginResponse;
        UserDTO userInfo = userService.login(id, password);

        if (userInfo == null) {
            return NOT_FOUND;
        } else {
            loginResponse = LoginResponse.success(userInfo);
            if (userInfo.getStatus() == ADMIN) {
                SessionUtil.setLoginAdminId(session, id);
            } else {
                SessionUtil.setLoginMemberId(session, id);
            }

            responseEntity = new ResponseEntity<>(loginResponse, OK);
        }

        log.info("log in user : " + id);
        log.info("log in status : " + userInfo.getStatus());

        return OK;
    }

    @GetMapping("my-info")
    public UserInfoResponse memberInfo(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);

        if (id == null) {
            id = SessionUtil.getLoginAdminId(session);
        }

        UserDTO memberInfo = userService.getUserInfo(id);
        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<LoginResponse> updateUserPassword(String userId,
                                                            @RequestBody UserUpdatePasswordRequest request,
                                                            HttpSession session) {
        System.out.println(userId);
        ResponseEntity<LoginResponse> responseEntity;

        String beforePassword = request.getBeforePassword();
        String afterPassword = request.getAfterPassword();

        try {
            userService.updatePassword(userId, beforePassword, afterPassword);
            responseEntity = new ResponseEntity<>(OK);
        } catch (IllegalArgumentException e) {
            log.error("updatePassword ERROR!", e);
            responseEntity = new ResponseEntity<>(BAD_REQUEST);
        }

        return responseEntity;
    }

    @DeleteMapping("delete")
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String userId = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(userId, userDeleteId.getPassword());
            responseEntity = new ResponseEntity<>(OK);
        } catch (RuntimeException e) {
            log.error("deleteId ERROR!", e);
            responseEntity = new ResponseEntity<>(BAD_REQUEST);
        }
        return responseEntity;
    }
}
