package org.web.gamedev.rpg.forum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.web.gamedev.rpg.forum.model.dto.UserDto;
import org.web.gamedev.rpg.forum.model.entity.UserEntity;
import org.web.gamedev.rpg.forum.model.payload.CustomUserDetails;

@Mapper(componentModel = "spring")
public abstract class UserDetailsMapper {
    public static final UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);

    public abstract CustomUserDetails getUserDetailsFromUserEntity(UserEntity userEntity);

    public abstract UserDto getUserDtoFromUserDetails(CustomUserDetails userDetails);

}
