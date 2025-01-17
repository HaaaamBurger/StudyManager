package org.studymanager.main.service.impl;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.studymanager.main.model.teacher.Teacher;
import org.studymanager.main.model.teacher.TeacherRequest;
import org.studymanager.main.model.teacher.dto.TeacherResponse;
import org.studymanager.main.model.teacher.mapper.TeacherMapper;
import org.studymanager.main.common.UserRole;
import org.studymanager.main.common.UserType;
import org.studymanager.main.repository.TeacherRepository;
import org.studymanager.main.service.UserCrudService;
import org.studymanager.main.service.common.utils.EntityServiceUtils;
import org.studymanager.main.service.common.utils.TeacherServiceUtils;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements UserCrudService<TeacherResponse, TeacherRequest> {
  private final TeacherRepository teacherRepository;
  private final TeacherMapper teacherMapper;
  private final TeacherServiceUtils teacherServiceUtils;
  private final EntityServiceUtils entityServiceUtils;
  private final PasswordEncoder passwordEncoder;

  @Override
  public List<TeacherResponse> getAll() {
    List<Teacher> allTeachers = teacherRepository.findAll();
    log.info("[TEACHER_SERVICE]: Teacher list: {}", allTeachers);

    return teacherMapper.entityListToResponseList(allTeachers);
  }

  @Override
  public TeacherResponse create(TeacherRequest teacherRequest) {
    Teacher teacher = teacherMapper.requestToEntity(teacherRequest);

    String email = teacher.getEmail();


    boolean isEntityExists = entityServiceUtils.isEntityExists(email);

    if (isEntityExists) {
      throw new EntityExistsException("Entity with email: " + email + " already exists.");
    }

    teacher.setCreatedAt(LocalDateTime.now());
    teacher.setRole(UserRole.USER);
    teacher.setUserType(UserType.TEACHER);
    teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
    Teacher savedTeacher = teacherRepository.save(teacher);

    return teacherMapper.entityToResponse(savedTeacher);
  }

  @Override
  public void delete(String id) {
    Teacher teacher = teacherServiceUtils.readById(id);
    teacherRepository.delete(teacher);
  }

  @Override
  public TeacherResponse update(String id, TeacherRequest teacherRequest) {
    Teacher teacher = teacherServiceUtils.readById(id);

    if (teacherRequest.getFirstName() != null) {
      teacher.setFirstName(teacherRequest.getFirstName());
    }
    if (teacherRequest.getLastName() != null) {
      teacher.setLastName(teacherRequest.getLastName());
    }
    if (teacherRequest.getEmail() != null) {
      teacher.setEmail(teacherRequest.getEmail());
    }

    teacher.setUpdatedAt(LocalDateTime.now());

    teacherRepository.save(teacher);

    return teacherMapper.entityToResponse(teacher);
  }

  @Override
  public TeacherResponse getById(String id) {
    Teacher teacher = teacherServiceUtils.readById(id);
    return teacherMapper.entityToResponse(teacher);
  }
}
