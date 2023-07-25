package com.app.services.user;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.user.UserBodyDTO;
import com.app.dto.user.UserDTO;
import com.app.dto.user.UserMapper;

import com.app.entities.User;
import com.app.repositories.BaseRepository;
import com.app.repositories.UserRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

  @Autowired
  protected UserRepository userRepository;

  public UserServiceImpl(BaseRepository<User, Long> baseRepository) {
    super(baseRepository);
  }

  /** REGISTER USER **/

  @Override
  public User register(UserBodyDTO user) throws Exception {

    try {
      // check mail:
      Optional<User> checkMail = userRepository.findByEmail(user.getEmail());

      if (checkMail.isPresent()) {
        throw new Exception("EMAIL_IN_USE");
      }
      Optional<User> checkDNI = userRepository.findByDni(user.getDni());
      if (checkDNI.isPresent()) {
        throw new Exception("DNI_IN_USE");
      }

      // Hash password:
      int strength = 10;
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

      String encodePass = bCryptPasswordEncoder.encode(user.getPassword());

      user.setPassword(encodePass);

      User userEntity = UserMapper.INSTANCE.toUser(user);
      userEntity.setRol("customer");
      return userRepository.save(userEntity);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

  }

  /** GET BY ID **/

  @Override
  public UserDTO getById(Long id) throws Exception {
    try {

      Optional<User> user = userRepository.findById(id);
      if (!user.isPresent()) {
        throw new Exception("USER_NOT_FOUND");
      }

      return UserMapper.INSTANCE.toUserDTO(user.get());

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /** LIST USER **/

  @Override
  public Page<UserDTO> list(Pageable pageable) throws Exception {
    try {

      Page<User> buyersEntities = userRepository.listUsers(pageable);

      return UserMapper.INSTANCE.toUsersDTO(buyersEntities);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

  }

  /** UPDATE USER **/

  @Override
  public UserDTO update(Long id, UserBodyDTO userdto) throws Exception {
    try {
      // check if exists:
      Optional<User> uo = userRepository.findById(id);
      if (!uo.isPresent()) {
        throw new Exception("USER_NOT_FOUND");
      }
      User oldUser = uo.get();

      // check email:
      if (!oldUser.getEmail().equals(userdto.getEmail())) {
        Optional<User> checkMail = userRepository.findByEmail(userdto.getEmail());
        if (checkMail.isPresent()) {
          throw new Exception("MAIL_IN_USE");
        }
      }

      // check dni:
      if (oldUser.getDni() != userdto.getDni()) {
        Optional<User> checkDNI = userRepository.findByDni(userdto.getDni());
        if (checkDNI.isPresent()) {
          throw new Exception("DNI_IN_USE");
        }

      }

      User updateUser = UserMapper.INSTANCE.toUser(userdto);

      // Encrypt pass:
      int strength = 10;
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

      String encodePass = bCryptPasswordEncoder.encode(updateUser.getPassword());

      updateUser.setPassword(encodePass);

      // linking the ids for update link in the repository:
      updateUser.setId(id);
      updateUser.getAddress().setId(oldUser.getAddress().getId());
      updateUser.setRol(oldUser.getRol());

      return UserMapper.INSTANCE.toUserDTO(userRepository.save(updateUser));

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
