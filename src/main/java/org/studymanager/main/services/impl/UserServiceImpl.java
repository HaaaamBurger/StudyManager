package org.studymanager.main.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.studymanager.main.common.UserRole;
import org.studymanager.main.model.user.User;
import org.studymanager.main.model.user.UserMapper;
import org.studymanager.main.model.user.UserResponse;
import org.studymanager.main.repositories.UserRepository;
import org.studymanager.main.services.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getAll() {
        List<User> allUsers = userRepository.findAll();
        return userMapper.entityListToResponseList(allUsers);
    }

    @Override
    public UserResponse readByEmail(String email) {
        User user = (User) loadUserByUsername(email);
        return userMapper.entityToResponse(user);
    }

    @Override
    public UserResponse create(User user) {
        String email = user.getEmail();
        boolean isUserExists = isUserExists(email);

        if (isUserExists) {
            throw new EntityNotFoundException("User by email " + email + " wasn't found.");
        }

        user.setCreatedAt(LocalDateTime.now());
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.entityToResponse(savedUser);
    }

    @Override
    public void delete(String id) {

    }

    public boolean isUserExists(String email) {
        return userRepository
                .readByEmail(email)
                .isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        User user = userRepository
                .readByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User by email " + email + " wasn't found."));

        return user;
    }
}
