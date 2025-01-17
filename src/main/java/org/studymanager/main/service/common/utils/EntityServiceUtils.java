package org.studymanager.main.service.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.studymanager.main.repository.StudentRepository;
import org.studymanager.main.repository.TeacherRepository;

@Service
@RequiredArgsConstructor
public class EntityServiceUtils {
  private final TeacherRepository teacherRepository;
  private final StudentRepository studentRepository;

  public boolean isEntityExists(String email) {
    boolean isTeacherExists = teacherRepository
            .readByEmail(email)
            .isPresent();

    boolean isStudentExists = studentRepository
            .readByEmail(email)
            .isPresent();

    return isStudentExists || isTeacherExists;
  }
}
