package com.app.dto.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.app.entities.User;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "rol", ignore = true)
  User toUser(UserBodyDTO userbody);

  UserDTO toUserDTO(User user);

  default Page<UserDTO> toUsersDTO(Page<User> users) {
    return users.map(this::toUserDTO);
  }

}
