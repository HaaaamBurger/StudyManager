package org.studymanager.main.teacher;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.studymanager.main.common.UserRole;
import org.studymanager.main.common.UserType;
import org.studymanager.main.model.teacher.Teacher;
import java.time.LocalDateTime;

class TeacherTest {

  private Teacher teacher;

  @BeforeEach
  public void setUp() {
    this.teacher = Teacher.builder()
            .firstName("John")
            .lastName("Doe")
            .email("test@gmail.com")
            .password("Test1234")
            .role(UserRole.USER)
            .createdAt(LocalDateTime.now())
            .userType(UserType.TEACHER)
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testEqualsAndHashCode() {

    Teacher teacher = Teacher.builder()
            .firstName("John")
            .lastName("Doe")
            .email("test@gmail.com")
            .password("1111")
            .role(UserRole.USER)
            .createdAt(LocalDateTime.now())
            .userType(UserType.TEACHER)
            .updatedAt(LocalDateTime.now())
            .build();

    assertEquals(teacher, this.teacher);
    assertEquals(teacher.hashCode(), this.teacher.hashCode());
  }

  @Test
  void testPasswordValid() {

    assertTrue(this.teacher
            .getPassword()
            .matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    );
  }

  @Test
  void testPasswordNotValid() {
    this.teacher.setPassword("1111");

    assertFalse(this.teacher
            .getPassword()
            .matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    );
  }

  @Test
  void testEmailValid() {
    assertTrue(this.teacher
            .getEmail()
            .matches("[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}")
    );
  }

  @Test
  void testEmailNotValid() {
    this.teacher.setEmail("testgmail.com");

    assertFalse(this.teacher
            .getEmail()
            .matches("[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}")
    );
  }

  @Test
  void shouldReturnRoleAsAuthority() {
    assertNotNull(teacher.getAuthorities());
    assertEquals(1, teacher.getAuthorities().size());
    assertTrue(teacher.getAuthorities().contains(UserRole.USER));
  }

  @Test
  void testFirstNameValid() {
    assertTrue(teacher
            .getFirstName()
            .matches("[A-Z][a-z]+")
    );
  }

  @Test
  void testFirstNameNotValid() {
    teacher.setFirstName("test");

    assertFalse(teacher
            .getFirstName()
            .matches("[A-Z][a-z]+")
    );
  }

  @Test
  void testLastNameValid() {
    assertTrue(teacher
            .getLastName()
            .matches("[A-Z][a-z]+")
    );
  }

  @Test
  void testLastNameNotValid() {
    teacher.setLastName("test");

    assertFalse(teacher
            .getLastName()
            .matches("[A-Z][a-z]+")
    );
  }

  @Test
  void testUserTypeEqualsToteacher() {
    assertEquals(UserType.TEACHER, teacher.getUserType());
  }

}