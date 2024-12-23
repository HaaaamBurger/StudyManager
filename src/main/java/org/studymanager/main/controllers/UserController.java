package org.studymanager.main.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.studymanager.main.model.user.User;
import org.studymanager.main.model.user.UserMapper;
import org.studymanager.main.model.user.UserRequest;
import org.studymanager.main.model.user.UserResponse;
import org.studymanager.main.services.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest userRequest) {
        User user = userMapper.requestToEntity(userRequest);
        return ResponseEntity.ok(userService.create(user));
    }
}


