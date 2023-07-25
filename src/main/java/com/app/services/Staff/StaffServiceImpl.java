package com.app.services.Staff;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.dto.staff.StaffBodyDTO;
import com.app.dto.staff.StaffDTO;
import com.app.dto.staff.StaffMapper;
import com.app.entities.Branch;
import com.app.entities.Staff;
import com.app.entities.User;
import com.app.repositories.BaseRepository;
import com.app.repositories.BranchRepository;
import com.app.repositories.StaffRepository;
import com.app.repositories.UserRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class StaffServiceImpl extends BaseServiceImpl<Staff, Long> implements StaffService {

  @Autowired
  StaffRepository staffRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  BranchRepository branchRepository;

  public StaffServiceImpl(BaseRepository<Staff, Long> baseRepository) {
    super(baseRepository);
  }

  /* REGISTER STAFF */

  @Override
  public StaffDTO register(StaffBodyDTO staffbody) throws Exception {

    try {
      // check if there is another staff with the same user_id:
      Optional<Staff> checkUserId = staffRepository.findByUserId(staffbody.getUser_id());

      if (checkUserId.isPresent()) {
        throw new Exception("STAFF_ALREADY_REGISTERED_WITH_THIS_USER_ID");
      }

      // check branch:
      Optional<Branch> branchOptional = branchRepository.findById(staffbody.getBranch_id());

      if (!branchOptional.isPresent()) {
        throw new Exception("BRANCH_NOT_FOUND");
      }
      Branch branch = branchOptional.get();

      // check if user exists:
      Optional<User> userOptional = userRepository.findById(staffbody.getUser_id());

      if (!userOptional.isPresent()) {
        throw new Exception("USER_NOT_FOUND");
      }
      User user = userOptional.get();

      Staff staff = StaffMapper.INSTANCE.toStaff(staffbody);

      String staffRole = staffbody.getRol();

      if (!staffRole.equals("supervisor") && !staffRole.equals("rrhh") && !staffRole.equals("storer")) {
        throw new Exception("INVALID_ROL");
      }
      // updating rol:
      user.setRol(staffRole);
      userRepository.save(user);

      staff.setBranch(branch);
      staff.setUser(user);
      staff.setLaborDischarge(new Date());

      return StaffMapper.INSTANCE.toStaffDTO(staffRepository.save(staff));

    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new Exception(e.getMessage());

    }
  }

  /* GET BY ID */

  @Override
  public StaffDTO getById(Long id) throws Exception {

    try {

      Optional<Staff> staff = staffRepository.findById(id);
      if (!staff.isPresent()) {
        throw new Exception("STAFF_NOT_FOUND");
      }

      return StaffMapper.INSTANCE.toStaffDTO(staff.get());

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public StaffDTO update(Long id, StaffBodyDTO staffbody) throws Exception {

    try {
      Optional<Staff> staffOptional = staffRepository.findById(id);
      // check for staff:
      if (!staffOptional.isPresent()) {
        throw new Exception("STAFF_NOT_FOUND");
      }
      Staff oldStaff = staffOptional.get();

      Optional<User> userOptional = userRepository.findById(staffbody.getUser_id());

      if (!userOptional.isPresent()) {
        throw new Exception("USER_NOT_FOUND");

      }
      User user = userOptional.get();

      Optional<Branch> brancOptional = branchRepository.findById(staffbody.getBranch_id());

      if (!brancOptional.isPresent()) {
        throw new Exception("BRANCH_NOT_FOUND");
      }

      Staff staff = StaffMapper.INSTANCE.toStaff(staffbody);

      // check if the rol is valid:
      String role = staffbody.getRol();
      if (!role.equals("rrhh") && !role.equals("supervisor") && !role.equals("storer")) {
        throw new Exception("INVALID_ROL");
      }

      // check if rol has changed:
      if (!oldStaff.getUser().getRol().equals(staffbody.getRol())) {
        user.setRol(staffbody.getRol());
        userRepository.save(user);
      }

      staff.setId(id); // linking id
      staff.setUser(user); // linking user
      staff.setBranch(brancOptional.get());
      staff.setLaborDischarge(oldStaff.getLaborDischarge());

      return StaffMapper.INSTANCE.toStaffDTO(staffRepository.save(staff));

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public Page<StaffDTO> list(Pageable pageable) throws Exception {

    try {

      Page<Staff> pageStaff = staffRepository.list(pageable);

      return StaffMapper.INSTANCE.toStaffsDTO(pageStaff);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
