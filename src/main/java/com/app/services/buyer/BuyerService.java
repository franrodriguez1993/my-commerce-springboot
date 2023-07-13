package com.app.services.buyer;

import com.app.dto.user.BuyerBodyDTO;
import com.app.dto.user.BuyerDTO;
import com.app.entities.Buyer;
import com.app.services.base.BaseService;

public interface BuyerService extends BaseService<Buyer, Long> {

  Buyer register(BuyerBodyDTO buyer) throws Exception;

  BuyerDTO getById(Long id) throws Exception;
}
