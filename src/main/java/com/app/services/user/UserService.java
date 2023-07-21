package com.app.services.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.user.UserBodyDTO;
import com.app.dto.user.UserDTO;
import com.app.entities.User;
import com.app.services.base.BaseService;

public interface UserService extends BaseService<User, Long> {

  User register(UserBodyDTO buyer) throws Exception;

  UserDTO update(Long id, UserBodyDTO buyer) throws Exception;

  Page<UserDTO> list(Pageable pageable) throws Exception;

  UserDTO getById(Long id) throws Exception;
}
