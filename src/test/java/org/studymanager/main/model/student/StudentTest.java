package org.studymanager.main.model.student;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.studymanager.main.common.UserRole;
import org.studymanager.main.common.UserType;
import java.time.LocalDateTime;

class StudentTest {

  @Test
  void testEqualsAndHashCode() {

    Student student = Student.builder()
            .id("uuid")
            .firstName("John")
            .lastName("Doe")
            .email("test@gmail.com")
            .password("1111")
            .role(UserRole.USER)
            .createdAt(LocalDateTime.now())
            .userType(UserType.STUDENT)
            .updatedAt(LocalDateTime.now())
            .build();

    Student student1 = Student.builder()
            .id("uuid")
            .firstName("John")
            .lastName("Doe")
            .email("test@gmail.com")
            .password("1111")
            .role(UserRole.USER)
            .createdAt(LocalDateTime.now())
            .userType(UserType.STUDENT)
            .updatedAt(LocalDateTime.now())
            .build();

    assertEquals(student, student1);
  }
}