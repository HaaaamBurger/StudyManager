package org.studymanager.main.service;

import org.studymanager.main.model.auth.dto.AuthRequest;
import org.studymanager.main.model.auth.dto.AuthResponse;
import org.studymanager.main.model.student.dto.StudentRequest;
import org.studymanager.main.model.teacher.TeacherRequest;

/**
 * This interface is needed for defining authentication
 * controller methods.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
public interface AuthenticationService {
  /**
   * Sign-in method for creating new user.
   */
  String signIn(AuthRequest authRequest);

  /**
   * Sign-up method for creating new teacher.
   */
  AuthResponse teacherSignUp(TeacherRequest teacherRequest);

  /**
   * Sign-up method for creating new student.
   */
  AuthResponse studentSignUp(StudentRequest studentRequest);

}
