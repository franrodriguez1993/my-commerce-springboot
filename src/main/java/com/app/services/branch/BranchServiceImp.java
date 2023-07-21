package com.app.services.branch;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.dto.branch.BranchBodyDTO;
import com.app.dto.branch.BranchDTO;
import com.app.dto.branch.BranchMapper;
import com.app.dto.product.BranchProductAloneDTO;
import com.app.dto.product.BranchProductBodyDTO;
import com.app.dto.product.BranchProductDTO;
import com.app.dto.product.ProductMapper;
import com.app.entities.Branch;
import com.app.entities.BranchProduct;
import com.app.entities.Product;
import com.app.repositories.BaseRepository;
import com.app.repositories.BranchProductRepository;
import com.app.repositories.BranchRepository;
import com.app.repositories.ProductRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class BranchServiceImp extends BaseServiceImpl<Branch, Long> implements BranchService {

  @Autowired
  BranchRepository branchRepository;

  @Autowired
  BranchProductRepository branchProductRepository;

  @Autowired
  ProductRepository productRepository;

  public BranchServiceImp(BaseRepository<Branch, Long> baseRepository) {
    super(baseRepository);
  }

  /* CREATE BRANCH */

  @Override
  public BranchDTO create(BranchBodyDTO branchdto) throws Exception {

    try {
      Branch branchEntity = BranchMapper.INSTANCE.toBranch(branchdto);
      return BranchMapper.INSTANCE.toBranchDTO(branchRepository.save(branchEntity));

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* FIND BY ID */

  @Override
  public BranchDTO findById(Long id) throws Exception {

    try {
      Optional<Branch> branchOptional = branchRepository.findById(id);

      if (!branchOptional.isPresent()) {
        throw new Exception("BRANCH_NOT_FOUND");
      }

      return BranchMapper.INSTANCE.toBranchDTO(branchOptional.get());

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* LIST BRANCHS */
  @Override
  public Page<BranchDTO> list(Pageable pageable) throws Exception {

    try {

      Page<Branch> branches = branchRepository.findAll(pageable);

      return BranchMapper.INSTANCE.toBranchsDTO(branches);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* LIST BRANCH PRODUCK STOCK */
  @Override
  public Page<BranchProductAloneDTO> listStockByBranch(Long id, Pageable pageable) throws Exception {
    try {

      Optional<Branch> branchOptional = branchRepository.findById(id);

      if (!branchOptional.isPresent()) {
        throw new Exception("BRANCH_NOT_FOUND");
      }

      return ProductMapper.INSTANCE.toBranchProductDTO(branchProductRepository.listProductByStock(id, pageable));

    } catch (Exception e) {

      throw new Exception(e.getMessage());
    }
  }

  /* UPDATE BRANCH PRODUCK STOCK */

  @Override
  public BranchProductDTO updateStock(Long bid, Long pid, BranchProductBodyDTO bpbdto) throws Exception {
    try {

      Optional<Product> productOptional = productRepository.findById(pid);
      if (!productOptional.isPresent()) {
        throw new Exception("PRODUCT_NOT_FOUND");
      }

      Optional<Branch> branchOptional = branchRepository.findById(bid);
      if (!branchOptional.isPresent()) {
        throw new Exception("BRANCH_NOT_FOUND");

      }

      BranchProduct branchProduct = ProductMapper.INSTANCE.toBranchProduct(bpbdto);
      // check if exists:
      Optional<BranchProduct> branchProductOptional = branchProductRepository.findByProductAndBranch(bid, pid);

      // if exists, link the id:
      if (branchProductOptional.isPresent()) {
        branchProduct.setId(branchProductOptional.get().getId());

      }

      branchProduct.setBranch(branchOptional.get()); // link the branch
      branchProduct.setProduct(productOptional.get()); // link the product
      branchProduct.setQuantity(bpbdto.getQuantity());

      return ProductMapper.INSTANCE.toBranchProductDTO(branchProductRepository.save(branchProduct));

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
