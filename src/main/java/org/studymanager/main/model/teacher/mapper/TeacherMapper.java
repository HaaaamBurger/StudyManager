package org.studymanager.main.model.teacher.mapper;

import org.mapstruct.Mapper;
import org.studymanager.main.common.config.MapperConfig;
import org.studymanager.main.model.teacher.Teacher;
import org.studymanager.main.model.teacher.TeacherRequest;
import org.studymanager.main.model.teacher.dto.TeacherResponse;
import java.util.List;

@Mapper(config = MapperConfig.class)
public interface TeacherMapper {
  TeacherResponse entityToResponse(Teacher student);

  Teacher requestToEntity(TeacherRequest studentRequest);

  List<TeacherResponse> entityListToResponseList(List<Teacher> users);
}
