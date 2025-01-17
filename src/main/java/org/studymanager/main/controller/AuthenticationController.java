package org.studymanager.main.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studymanager.main.model.auth.dto.AuthRequest;
import org.studymanager.main.model.auth.dto.AuthResponse;
import org.studymanager.main.model.student.dto.StudentRequest;
import org.studymanager.main.model.teacher.TeacherRequest;
import org.studymanager.main.service.AuthenticationService;

/**
 * This class is needed to receive requests
 * for user authentication processing.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  /**
   * Sign-in endpoint for verifying new user.
   *
   * @param authRequest user passes email and password into this body.
   * @return String
   */
  @PostMapping("/sign-in")
  public ResponseEntity<String> signIn(
          @Valid @RequestBody AuthRequest authRequest
  ) {
    return ResponseEntity.ok(authenticationService.signIn(authRequest));
  }

  /**
   * Sign-up endpoint for creating a student.
   *
   * @param studentRequest user passes firstname, lastname,
   *                       email and password into this body.
   * @return AuthResponse
   */
  @PostMapping("/student/sign-up")
  public ResponseEntity<AuthResponse> studentSignUp(@Valid @RequestBody StudentRequest studentRequest) {
    return ResponseEntity.ok(authenticationService.studentSignUp(studentRequest));
  }

  /**
   * Sign-up endpoint for creating a teacher.
   *
   * @param teacherRequest user passes firstname, lastname,
   *                       email and password into this body.
   * @return AuthResponse
   */
  @PostMapping("/teacher/sign-up")
  public ResponseEntity<AuthResponse> teacherSignUp(@Valid @RequestBody TeacherRequest teacherRequest) {
    return ResponseEntity.ok(authenticationService.teacherSignUp(teacherRequest));
  }
}