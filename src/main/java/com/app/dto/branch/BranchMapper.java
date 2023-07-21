package com.app.dto.branch;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.app.entities.Branch;

@Mapper
public interface BranchMapper {
  BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);

  @Mapping(target = "id", ignore = true)
  Branch toBranch(BranchBodyDTO branch);

  BranchDTO toBranchDTO(Branch branch);

  default Page<BranchDTO> toBranchsDTO(Page<Branch> branchs) {
    return branchs.map(this::toBranchDTO);
  }

}
