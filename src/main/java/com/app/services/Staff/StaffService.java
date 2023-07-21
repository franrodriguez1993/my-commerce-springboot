package com.app.services.Staff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.staff.StaffBodyDTO;
import com.app.dto.staff.StaffDTO;
import com.app.entities.Staff;
import com.app.services.base.BaseService;

public interface StaffService extends BaseService<Staff, Long> {

  StaffDTO register(StaffBodyDTO staffbody) throws Exception;

  StaffDTO update(Long id, StaffBodyDTO staffbody) throws Exception;

  StaffDTO getById(Long id) throws Exception;

  Page<StaffDTO> list(Pageable pageable) throws Exception;

}
