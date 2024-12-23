package org.studymanager.main.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.studymanager.main.model.user.User;
import org.studymanager.main.model.user.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<UserResponse> getAll();

    UserResponse readByEmail(String email);

    UserResponse create(User user);

    void delete(String id);
}
