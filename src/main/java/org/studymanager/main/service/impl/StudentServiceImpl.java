package org.studymanager.main.service.impl;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.studymanager.main.model.student.Student;
import org.studymanager.main.model.student.mapper.StudentMapper;
import org.studymanager.main.model.student.dto.StudentRequest;
import org.studymanager.main.model.student.dto.StudentResponse;
import org.studymanager.main.common.UserRole;
import org.studymanager.main.common.UserType;
import org.studymanager.main.repository.StudentRepository;
import org.studymanager.main.service.UserCrudService;
import org.studymanager.main.service.common.utils.EntityServiceUtils;
import org.studymanager.main.service.common.utils.StudentServiceUtils;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements UserCrudService<StudentResponse, StudentRequest> {
  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;
  private final StudentServiceUtils studentServiceUtils;
  private final EntityServiceUtils entityServiceUtils;
  private final PasswordEncoder passwordEncoder;

  @Override
  public List<StudentResponse> getAll() {
    List<Student> allStudents = studentRepository.findAll();
    log.info("[STUDENT_SERVICE]: Student list: {}", allStudents);

    return studentMapper.entityListToResponseList(allStudents);
  }

  @Override
  public StudentResponse create(StudentRequest studentRequest) {
    Student student = studentMapper.requestToEntity(studentRequest);

    String email = student.getEmail();
    boolean isEntityExists = entityServiceUtils.isEntityExists(email);

    if (isEntityExists) {
      throw new EntityExistsException("Entity with email: " + email + " already exists.");
    }

    student.setCreatedAt(LocalDateTime.now());
    student.setRole(UserRole.USER);
    student.setUserType(UserType.STUDENT);
    student.setPassword(passwordEncoder.encode(student.getPassword()));
    Student savedStudent = studentRepository.save(student);

    return studentMapper.entityToResponse(savedStudent);
  }

  @Override
  public void delete(String id) {
    Student student = studentServiceUtils.readById(id);
    studentRepository.delete(student);
  }

  @Override
  public StudentResponse update(String id, StudentRequest studentRequest) {
    System.out.println(studentRequest);
    Student student = studentServiceUtils.readById(id);

    if (studentRequest.getFirstName() != null) {
      student.setFirstName(studentRequest.getFirstName());
    }
    if (studentRequest.getLastName() != null) {
      student.setLastName(studentRequest.getLastName());
    }
    if (studentRequest.getEmail() != null) {
      student.setEmail(studentRequest.getEmail());
    }

    student.setUpdatedAt(LocalDateTime.now());

    studentRepository.save(student);

    return studentMapper.entityToResponse(student);
  }

  @Override
  public StudentResponse getById(String id) {
    Student student = studentServiceUtils.readById(id);
    return studentMapper.entityToResponse(student);
  }
}
