package org.studymanager.main.model.user;

import org.mapstruct.Mapper;
import org.studymanager.main.common.config.MapperConfig;

import java.util.List;


@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponse entityToResponse(User user);

    User requestToEntity(UserRequest userRequest);

    List<UserResponse> entityListToResponseList(List<User> users);
}
