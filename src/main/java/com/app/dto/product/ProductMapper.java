package com.app.dto.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.app.entities.BranchProduct;
import com.app.entities.Product;

@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  // To product
  @Mapping(target = "stock", ignore = true)
  @Mapping(target = "branch.address.id", ignore = true)
  Product toProduct(ProductDTO productdto);

  // to productDTO
  ProductDTO toProductDTO(Product product);

  // to product From body:
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "brand", ignore = true)
  @Mapping(target = "stock", ignore = true)
  Product toProductFromBody(ProductBodyDTO productbodydto);

  default Page<ProductDTO> toProductsDTO(Page<Product> products) {
    return products.map(this::toProductDTO);
  }

  // branch product mapper
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "branch", ignore = true)
  @Mapping(target = "product", ignore = true)
  BranchProduct toBranchProduct(BranchProductBodyDTO branchProductBodyDTO);

  // to branchproduct:
  BranchProductDTO toBranchProductDTO(BranchProduct branchProduct);

  // to branchproduct without branch
  BranchProductAloneDTO toBranchProductAloneDTO(BranchProduct branchproduct);

  // to page branchproduct
  default Page<BranchProductAloneDTO> toBranchProductDTO(Page<BranchProduct> branchProducts) {
    return branchProducts.map(this::toBranchProductAloneDTO);
  }

}
