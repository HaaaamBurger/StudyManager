package org.studymanager.main.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.studymanager.main.common.UserRole;
import org.studymanager.main.common.UserType;
import org.studymanager.main.model.student.Student;
import org.studymanager.main.repository.StudentRepository;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class StudentRepositoryTest {
  private final StudentRepository studentRepository;

  @Autowired
  public StudentRepositoryTest(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  private Student student;

  @BeforeEach
  public void setUp() {
    this.student = Student.builder()
            .firstName("John")
            .lastName("Doe")
            .email("test@gmail.com")
            .password("Password1111")
            .role(UserRole.USER)
            .userType(UserType.STUDENT)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

  }

  @Test
  public void testStudentCreation() {
    studentRepository.save(this.student);
    Optional<Student> studentById = studentRepository.findById(student.getId());
    assertTrue(studentById.isPresent());
  }

  @Test
  public void testGetAllStudents() {
    studentRepository.save(this.student);
    List<Student> allStudents = studentRepository.findAll();
    assertEquals(1, allStudents.size());
    assertEquals(this.student.getEmail(), allStudents.get(0).getEmail());
  }

  @Test
  public void testGetStudentById() {
    Student savedStudent = studentRepository.save(this.student);
    Optional<Student> studentById = studentRepository.findById(savedStudent.getId());
    assertTrue(studentById.isPresent());
    assertEquals(this.student.getEmail(), studentById.get().getEmail());
  }

  @Test
  public void testDeleteStudent() {
    Student savedStudent = studentRepository.save(this.student);
    studentRepository.delete(savedStudent);
    List<Student> all = studentRepository.findAll();
    assertEquals(0, all.size());
  }


  @Test
  public void testUniqueEmailConstraint() {
    studentRepository.save(this.student);

    Student student1 = Student.builder()
            .firstName("Jane")
            .lastName("Doe")
            .email(student.getEmail())
            .password("Password1111")
            .role(UserRole.USER)
            .userType(UserType.STUDENT)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    assertThrows(Exception.class, () -> studentRepository.saveAndFlush(student1));
  }
}
