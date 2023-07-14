package com.app.services.buyer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.user.BuyerBodyDTO;
import com.app.dto.user.BuyerDTO;
import com.app.entities.Buyer;
import com.app.services.base.BaseService;

public interface BuyerService extends BaseService<Buyer, Long> {

  Buyer register(BuyerBodyDTO buyer) throws Exception;

  Page<BuyerDTO> listBuyers(Pageable pageable) throws Exception;

  BuyerDTO getById(Long id) throws Exception;
}
