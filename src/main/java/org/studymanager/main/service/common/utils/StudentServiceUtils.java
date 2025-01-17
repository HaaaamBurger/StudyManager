package org.studymanager.main.service.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.studymanager.main.model.student.Student;
import org.studymanager.main.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentServiceUtils {
  private final StudentRepository studentRepository;

  public Student readById(String id) {
    return studentRepository
            .findById(id)
            .orElseThrow(() -> new UsernameNotFoundException(
                    "Student by id " + id + " wasn't found."
            ));
  }
}
