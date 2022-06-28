package org.web.gamedev.rpg.auth.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.web.gamedev.rpg.auth.model.User;
import org.web.gamedev.rpg.model.auth.UserDto;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

  public abstract UserDto getUserDtoFromUserEntity(User userEntity);

  public abstract List<UserDto> getUserDtoListFromUserEntityList(List<User> users);

  public abstract User getEntityFromUserDto(UserDto userDto);

  public abstract List<User> getUserEntityListFromUserDtoList(List<UserDto> users);
}
