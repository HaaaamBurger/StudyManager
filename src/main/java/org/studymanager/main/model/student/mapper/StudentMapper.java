package org.studymanager.main.model.student.mapper;

import org.mapstruct.Mapper;
import org.studymanager.main.common.config.MapperConfig;
import org.studymanager.main.model.student.Student;
import org.studymanager.main.model.student.dto.StudentRequest;
import org.studymanager.main.model.student.dto.StudentResponse;
import java.util.List;

@Mapper(config = MapperConfig.class)
public interface StudentMapper {
  StudentResponse entityToResponse(Student student);

  Student requestToEntity(StudentRequest studentRequest);

  List<StudentResponse> entityListToResponseList(List<Student> users);
}
