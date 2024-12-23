package org.studymanager.main.model.user;

import org.mapstruct.Mapper;
import org.studymanager.main.common.config.MapperConfig;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    public UserResponse entityToResponse(User user);

    public User requestToEntity(UserRequest userRequest);

    public List<UserResponse> entityListToResponseList(List<User> users);
}
