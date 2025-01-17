package org.studymanager.main.model.teacher;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.studymanager.main.model.common.dto.BaseRequest;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherRequest extends BaseRequest {
}
