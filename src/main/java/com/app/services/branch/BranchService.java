package com.app.services.branch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.branch.BranchBodyDTO;
import com.app.dto.branch.BranchDTO;
import com.app.dto.product.BranchProductAloneDTO;
import com.app.dto.product.BranchProductBodyDTO;
import com.app.dto.product.BranchProductDTO;
import com.app.entities.Branch;
import com.app.services.base.BaseService;

public interface BranchService extends BaseService<Branch, Long> {

  BranchDTO create(BranchBodyDTO brand) throws Exception;

  BranchDTO findById(Long id) throws Exception;

  Page<BranchDTO> list(Pageable pageable) throws Exception;

  Page<BranchProductAloneDTO> listStockByBranch(Long id, Pageable pageable) throws Exception;

  BranchProductDTO updateStock(Long bid, Long pid, BranchProductBodyDTO branchProductBodyDTO) throws Exception;
}
