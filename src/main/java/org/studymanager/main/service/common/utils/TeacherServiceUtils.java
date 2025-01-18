package org.studymanager.main.service.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.studymanager.main.model.teacher.Teacher;
import org.studymanager.main.repository.TeacherRepository;

@Service
@RequiredArgsConstructor
public class TeacherServiceUtils {
  private final TeacherRepository teacherRepository;

  public Teacher readById(String id) {
    return teacherRepository
            .readById(id)
            .orElseThrow(() -> new UsernameNotFoundException(
                    "Teacher by id " + id + " wasn't found."
            ));
  }
}
