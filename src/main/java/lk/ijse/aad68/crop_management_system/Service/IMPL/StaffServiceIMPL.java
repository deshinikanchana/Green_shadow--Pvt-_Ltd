package lk.ijse.aad68.crop_management_system.Service.IMPL;

import jakarta.transaction.Transactional;
import lk.ijse.aad68.crop_management_system.CustomOBJ.StaffMemberErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.StaffMemberResponse;
import lk.ijse.aad68.crop_management_system.DAO.StaffDAO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.StaffDTO;
import lk.ijse.aad68.crop_management_system.ENUMS.Designation;
import lk.ijse.aad68.crop_management_system.ENUMS.Role;
import lk.ijse.aad68.crop_management_system.Entity.StaffEntity;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.StaffMemberNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.StaffService;
import lk.ijse.aad68.crop_management_system.Util.AppUtil;
import lk.ijse.aad68.crop_management_system.Util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class StaffServiceIMPL implements StaffService {
    @Autowired
    private final Mapping mapping;
    @Autowired
    private StaffDAO staffDAO;
    @Override
    public void saveMember(StaffDTO dto) {
        String maxIndex = AppUtil.createStaffId(staffDAO.findMaxStaffId());
        if (maxIndex == null) {
            dto.setStaffId("Staff-0001");
        }else{
            dto.setStaffId(maxIndex);
        }
        StaffEntity savedMember =
                staffDAO.save(mapping.convertStaffDTOToEntity(dto));
        if(savedMember == null ) {
            throw new DataPersistFailedException("Cannot data saved");
        }
    }

    @Override
    public void updateMember(StaffDTO dto) {
        Optional<StaffEntity> tmpMember = staffDAO.findById(dto.getStaffId());
        if(!tmpMember.isPresent()){
            throw new StaffMemberNotFoundException("Staff Member not found");
        }else {
            tmpMember.get().setFirstName(dto.getFirstName());
            tmpMember.get().setLastName(dto.getLastName());
            tmpMember.get().setEmail(dto.getEmail());
            tmpMember.get().setAddressLine1(dto.getAddressLine1());
            tmpMember.get().setAddressLine2(dto.getAddressLine2());
            tmpMember.get().setAddressLine3(dto.getAddressLine3());
            tmpMember.get().setAddressLine4(dto.getAddressLine4());
            tmpMember.get().setAddressLine5(dto.getAddressLine5());
            tmpMember.get().setContactNo(dto.getContactNo());
            tmpMember.get().setDesignation(Designation.valueOf(dto.getDesignation()));
            tmpMember.get().setRole(Role.valueOf(dto.getRole()));
        }
    }

    @Override
    public void deleteMember(String staffId) {
        Optional<StaffEntity> selectedStaffId = staffDAO.findById(staffId);
        if(!selectedStaffId.isPresent()){
            throw new StaffMemberNotFoundException("Staff Member not found");
        }else {
            staffDAO.deleteById(staffId);
        }
    }

    @Override
    public StaffMemberResponse getSelectedMember(String staffId) {
        if(staffDAO.existsById(staffId)){
            StaffEntity staffEntityById = staffDAO.getReferenceById(staffId);
            return mapping.convertStaffEntityToDTO(staffEntityById);
        }else {
            return new StaffMemberErrorResponse(0, "Staff Member not found");
        }
    }

    @Override
    public List<StaffDTO> getAllMembers() {
        List<StaffEntity> getAllMembers = staffDAO.findAll();
        return mapping.convertStaffEntityListToDTOList(getAllMembers);
    }
}
