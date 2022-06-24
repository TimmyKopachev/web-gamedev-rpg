package org.web.gamedev.rpg.forum.mapper;

import org.mapstruct.Mapper;
import org.web.gamedev.rpg.forum.model.dto.UserDto;
import org.web.gamedev.rpg.forum.model.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto getUserDtoFromUserEntity(UserEntity userEntity);

    List<UserDto> getUserDtoListFromUserEntityList(List<UserEntity> users);

    UserEntity getEntityFromUserDto(UserDto userDto);

    List<UserEntity> getUserEntityListFromUserDtoList(List<UserDto> users);
}
