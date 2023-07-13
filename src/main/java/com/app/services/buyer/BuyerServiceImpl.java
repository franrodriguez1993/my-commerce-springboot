package com.app.services.buyer;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.user.BuyerBodyDTO;
import com.app.dto.user.BuyerDTO;
import com.app.entities.Buyer;
import com.app.dto.user.BuyerMapper;
import com.app.entities.User;
import com.app.repositories.BaseRepository;
import com.app.repositories.BuyerRepository;
import com.app.repositories.UserRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class BuyerServiceImpl extends BaseServiceImpl<Buyer, Long> implements BuyerService {

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected BuyerRepository buyerRepository;

  public BuyerServiceImpl(BaseRepository<Buyer, Long> baseRepository) {
    super(baseRepository);
  }

  @Override
  public Buyer register(BuyerBodyDTO buyer) throws Exception {

    try {
      // check mail:
      Optional<User> checkMail = userRepository.findByEmail(buyer.getUser().getEmail());

      if (checkMail.isPresent()) {
        throw new Exception("EMAIL_IN_USE");
      }
      Optional<User> checkDNI = userRepository.findByDni(buyer.getUser().getDni());
      if (checkDNI.isPresent()) {
        throw new Exception("DNI_IN_USE");
      }

      // Hash password:
      int strength = 10;
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

      String encodePass = bCryptPasswordEncoder.encode(buyer.getUser().getPassword());

      buyer.getUser().setPassword(encodePass);

      Buyer buyerEntity = BuyerMapper.INSTANCE.toBuyer(buyer);
      return buyerRepository.save(buyerEntity);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

  }

  @Override
  public BuyerDTO getById(Long id) throws Exception {
    try {

      Optional<Buyer> buyerEntity = buyerRepository.findById(id);
      if (!buyerEntity.isPresent()) {
        throw new Exception("BUYER_NOT_FOUND");
      }

      BuyerDTO bdto = BuyerMapper.INSTANCE.toBuyerDto(buyerEntity.get());
      return bdto;

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
