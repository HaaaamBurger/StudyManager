package org.studymanager.main.student;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.studymanager.main.common.UserRole;
import org.studymanager.main.common.UserType;
import org.studymanager.main.model.student.Student;
import java.time.LocalDateTime;

class StudentTest {

  private Student student;

  @BeforeEach
  public void setUp() {
    this.student = Student.builder()
            .firstName("John")
            .lastName("Doe")
            .email("test@gmail.com")
            .password("Test1234")
            .role(UserRole.USER)
            .createdAt(LocalDateTime.now())
            .userType(UserType.STUDENT)
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testEqualsAndHashCode() {

    Student student = Student.builder()
            .firstName("John")
            .lastName("Doe")
            .email("test@gmail.com")
            .password("Test1234")
            .role(UserRole.USER)
            .createdAt(LocalDateTime.now())
            .userType(UserType.STUDENT)
            .updatedAt(LocalDateTime.now())
            .build();

    assertEquals(student, this.student);
    assertEquals(student.hashCode(), this.student.hashCode());
  }

  @Test
  void testPasswordValid() {


    assertTrue(this.student
            .getPassword()
            .matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    );
  }

  @Test
  void testPasswordNotValid() {
    this.student.setPassword("1111");
    assertFalse(this.student
            .getPassword()
            .matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    );
  }

  @Test
  void testEmailValid() {
    assertTrue(this.student
            .getEmail()
            .matches("[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}")
    );
  }

  @Test
  void testEmailNotValid() {
    this.student.setEmail("testgmail.com");

    assertFalse(this.student
            .getEmail()
            .matches("[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}")
    );
  }

  @Test
  void shouldReturnRoleAsAuthority() {
    assertNotNull(student.getAuthorities());
    assertEquals(1, student.getAuthorities().size());
    assertTrue(student.getAuthorities().contains(UserRole.USER));
  }

  @Test
  void testFirstNameValid() {
    assertTrue(student
            .getFirstName()
            .matches("[A-Z][a-z]+")
    );
  }

  @Test
  void testFirstNameNotValid() {
    student.setFirstName("test");

    assertFalse(student
            .getFirstName()
            .matches("[A-Z][a-z]+")
    );
  }

  @Test
  void testLastNameValid() {
    assertTrue(student
            .getLastName()
            .matches("[A-Z][a-z]+")
    );
  }

  @Test
  void testLastNameNotValid() {
    student.setLastName("test");

    assertFalse(student
            .getLastName()
            .matches("[A-Z][a-z]+")
    );
  }

  @Test
  void testUserTypeEqualsToStudent() {
    assertEquals(UserType.STUDENT, student.getUserType());
  }
}