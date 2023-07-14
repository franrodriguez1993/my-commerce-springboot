package com.app.services.brand;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.dto.brand.BrandDTO;
import com.app.dto.brand.BrandMapper;
import com.app.entities.Brand;
import com.app.repositories.BaseRepository;
import com.app.repositories.BrandRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class BrandServiceImpl extends BaseServiceImpl<Brand, Long> implements BrandService {

  @Autowired
  BrandRepository brandRepository;

  public BrandServiceImpl(BaseRepository<Brand, Long> baseRepository) {
    super(baseRepository);
  }

  @Override
  public BrandDTO create(BrandDTO branddto) throws Exception {
    try {

      Optional<Brand> brand = brandRepository.findByName(branddto.getName());
      if (brand.isPresent()) {
        throw new Exception("BRAND_ALREADY_EXISTS");
      }
      Brand newBrand = brandRepository.save(BrandMapper.INSTANCE.toBrand(branddto));

      return BrandMapper.INSTANCE.toBrandDTO(newBrand);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

  }

  @Override
  public BrandDTO findByID(Long id) throws Exception {
    try {
      Optional<Brand> brand = brandRepository.findById(id);

      if (!brand.isPresent()) {
        throw new Exception("BRAND_NOT_FOUND");
      }

      return BrandMapper.INSTANCE.toBrandDTO(brand.get());

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

  }

  @Override
  public Page<BrandDTO> findAll(Pageable pageable) throws Exception {
    try {

      Page<Brand> brandList = brandRepository.listBrands(pageable);

      return BrandMapper.INSTANCE.toBrandDTOs(brandList);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
