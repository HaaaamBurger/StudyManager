package org.studymanager.main.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.studymanager.main.model.auth.dto.AuthRequest;
import org.studymanager.main.model.auth.dto.AuthResponse;
import org.studymanager.main.model.student.dto.StudentRequest;
import org.studymanager.main.model.student.dto.StudentResponse;
import org.studymanager.main.model.teacher.TeacherRequest;
import org.studymanager.main.model.teacher.dto.TeacherResponse;
import org.studymanager.main.common.UserType;
import org.studymanager.main.security.config.CustomUserDetailsService;
import org.studymanager.main.security.jwt.JwtTokenService;
import org.studymanager.main.service.AuthenticationService;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This class is needed for implementation
 * AuthenticationService interface.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final JwtTokenService jwtTokenService;
  private final CustomUserDetailsService customUserDetailsService;
  private final TeacherServiceImpl teacherService;
  private final StudentServiceImpl studentService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public String signIn(AuthRequest authRequest) {
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());

    if (passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
      log.info("[AUTHENTICATION_SIGNIN]: Successfully authenticated. {}", userDetails);
      return jwtTokenService.generateToken(userDetails);
    }

    log.error("[AUTHENTICATION_ERROR]: Incorrect credentials");
    return "Incorrect credentials!";
  }


  @Override
  public AuthResponse studentSignUp(StudentRequest studentRequest) {
    StudentResponse studentResponse = studentService.create(studentRequest);
    log.info("[AUTHENTICATION_STUDENT_SIGNUP]: New student successfully registered.");
    return AuthResponse.builder()
            .message("Student registered successfully!")
            .body(studentResponse)
            .build();
  }

  @Override
  public AuthResponse teacherSignUp(TeacherRequest teacherRequest) {
    TeacherResponse teacherResponse = teacherService.create(teacherRequest);
    log.info("[AUTHENTICATION_TEACHER_SIGNUP]: New teacher successfully registered.");
    return AuthResponse.builder()
            .message("Teacher registered successfully!")
            .body(teacherResponse)
            .build();
  }

  private UserType checkIfUserTypeValidElseThrowException(String userType) {

    boolean isAllowedUserType = Arrays.stream(UserType.values())
            .anyMatch(allowedUserType -> userType.toUpperCase(Locale.ROOT).equals(allowedUserType.toString()));
    if (!isAllowedUserType) {
      throw new IllegalArgumentException("User type: " + userType + " is not allowed! Try to use: " + Arrays.stream(UserType.values())
              .map(allowedUserType -> allowedUserType.toString().toLowerCase(Locale.ROOT))
              .collect(Collectors.joining(", ")));
    }

    return UserType.valueOf(userType.toUpperCase(Locale.ROOT));
  }
}