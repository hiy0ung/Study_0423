package com.study.bookstore.api.user;

import com.study.bookstore.api.user.dto.request.UserRequest;
import com.study.bookstore.api.user.dto.request.UserUpdateRequest;
import com.study.bookstore.api.user.dto.response.UserResponse;
import com.study.bookstore.global.jwt.CustomUserDetails;
import com.study.bookstore.service.user.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserFacade userFacade;

    @PostMapping("/register")
    public ResponseEntity<Long> createUser(
            @Valid @RequestBody UserRequest request
    ) {
        return ResponseEntity.status(201).body(userFacade.createUser(request));
    }

    /**
     * api 주소 "/me"
     * GetMapping
     * return UserResponse
     * method getCurrentUser
     * parameter -> 직접적으로 userId를 받아서는 안 됨 (시큐리티에서 찾아내야됨)
     * userId 기준으로 해당 유저를 찾아내기
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getUser().getId();
        return ResponseEntity.ok(userFacade.getUserById(userId));
    }

    /**
     * api 주소 "/me"
     * PutMapping
     * return UserResponse
     * method updateCurrentUser
     * parameter -> 직접적으로 userId를 받아서는 안 됨 (시큐리티에서 찾아내야됨)
     * body -> UserUpdateRequest
     * userId 기준으로 해당 유저를 찾아내기
     */
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody UserUpdateRequest request) {
        Long userId = user.getUser().getId();
        return ResponseEntity.ok(userFacade.updateUser(userId, request));
    }

    /**
     * api 주소 "/me"
     * DeleteMapping
     * return void
     * method deleteCurrentUser
     * parameter -> 직접적으로 userId를 받아서는 안 됨 (시큐리티에서 찾아내야됨)
     * body -> UserUpdateRequest
     * userId 기준으로 해당 유저를 찾아내기
     */
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentUser(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getUser().getId();
        userFacade.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * api 주소 "/{userId}"
     * DeleteMapping
     * return userResponse
     * method getUser
     * parameter -> 직접적으로 userId를 받기
     * body -> UserUpdateRequest
     * userId 기준으로 해당 유저를 찾아내기
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser (@PathVariable Long userId) {
        return ResponseEntity.ok(userFacade.getUserById(userId));
    }
}
