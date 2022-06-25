package org.web.gamedev.rpg.forum.mapper;

import org.mapstruct.Mapper;
import org.web.gamedev.rpg.forum.model.entity.UserDto;
import org.web.gamedev.rpg.forum.model.entity.UserEntity;
import org.web.gamedev.rpg.forum.model.payload.CustomUserDetails;

@Mapper(componentModel = "spring")
public abstract class UserDetailsMapper {
    public abstract CustomUserDetails getUserDetailsFromUserEntity(UserEntity userEntity);

    public abstract UserDto getUserDtoFromUserDetails(CustomUserDetails userDetails);

}
