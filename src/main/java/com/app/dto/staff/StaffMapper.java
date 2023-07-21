package com.app.dto.staff;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.app.entities.Staff;

@Mapper
public interface StaffMapper {

  StaffMapper INSTANCE = Mappers.getMapper(StaffMapper.class);

  // to staff
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "branch", ignore = true)
  Staff toStaff(StaffBodyDTO staffbody);

  // to staffdto
  StaffDTO toStaffDTO(Staff staff);

  // page staffs
  default Page<StaffDTO> toStaffsDTO(Page<Staff> staffs) {
    return staffs.map(this::toStaffDTO);
  }
}
