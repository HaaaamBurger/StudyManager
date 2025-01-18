package org.studymanager.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studymanager.main.model.student.Student;
import org.studymanager.main.model.teacher.Teacher;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
  Optional<Teacher> readByEmail(String email);

  Optional<Teacher> readById(String id);
}
