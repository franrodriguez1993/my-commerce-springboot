package com.app.services.buyer;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  /** REGISTER **/

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

  /** GET BY ID **/

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

  /** LIST BUYER **/

  @Override
  public Page<BuyerDTO> listBuyers(Pageable pageable) throws Exception {
    try {

      Page<Buyer> buyersEntities = buyerRepository.pageBuyers("", pageable);

      return BuyerMapper.INSTANCE.toBuyerDtos(buyersEntities);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

  }

  /** UPDATE BUYER **/

  @Override
  public BuyerDTO update(Long id, BuyerBodyDTO buyerdto) throws Exception {
    try {
      // check if exists:
      Optional<Buyer> bo = buyerRepository.findById(id);
      if (!bo.isPresent()) {
        throw new Exception("BUYER_NOT_FOUND");
      }
      Buyer oldBuyer = bo.get();

      // check email:
      if (!oldBuyer.getUser().getEmail().equals(buyerdto.getUser().getEmail())) {
        Optional<User> checkMail = userRepository.findByEmail(buyerdto.getUser().getEmail());
        if (checkMail.isPresent()) {
          throw new Exception("MAIL_IN_USE");
        }
      }

      // check dni:
      if (oldBuyer.getUser().getDni() != buyerdto.getUser().getDni()) {
        Optional<User> checkDNI = userRepository.findByDni(buyerdto.getUser().getDni());
        if (checkDNI.isPresent()) {
          throw new Exception("DNI_IN_USE");
        }

      }

      Buyer buyer = BuyerMapper.INSTANCE.toBuyer(buyerdto);

      // Encrypt pass:
      int strength = 10;
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

      String encodePass = bCryptPasswordEncoder.encode(buyer.getUser().getPassword());

      buyer.getUser().setPassword(encodePass);

      // linking the ids for update link in the repository:
      buyer.setId(id);
      buyer.getUser().setId(oldBuyer.getUser().getId());
      buyer.getUser().getAddress().setId(oldBuyer.getUser().getAddress().getId());

      Buyer updatedBuyer = buyerRepository.save(buyer);
      return BuyerMapper.INSTANCE.toBuyerDto(updatedBuyer);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
