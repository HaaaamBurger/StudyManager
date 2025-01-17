package org.studymanager.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studymanager.main.model.student.Student;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
  Optional<Student> readByEmail(String email);
}
