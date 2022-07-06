package org.web.gamedev.rpg.auth.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.web.gamedev.rpg.auth.model.User;
import org.web.gamedev.rpg.model.auth.UserDto;

@Mapper(
    componentModel = "spring",
    uses = {PasswordEncoder.class})
public abstract class UserMapper {

  public abstract UserDto getUserDtoFromUserEntity(User user);

  public abstract List<UserDto> getUserDtoListFromUserEntityList(List<User> users);

  @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDto.getPassword()))")
  public abstract User getEntityFromUserDto(UserDto userDto);

  public abstract List<User> getUserEntityListFromUserDtoList(List<UserDto> users);
}
