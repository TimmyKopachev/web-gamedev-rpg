package org.web.gamedev.rpg.auth.mapper;

import org.mapstruct.Mapper;
import org.web.gamedev.rpg.auth.model.User;
import org.web.gamedev.rpg.auth.model.payload.CustomUserDetails;
import org.web.gamedev.rpg.model.auth.UserDto;

@Mapper(componentModel = "spring")
public abstract class UserDetailsMapper {

  public abstract CustomUserDetails getUserDetailsFromUserEntity(User user);

  public abstract UserDto getUserDtoFromUserDetails(CustomUserDetails userDetails);
}
