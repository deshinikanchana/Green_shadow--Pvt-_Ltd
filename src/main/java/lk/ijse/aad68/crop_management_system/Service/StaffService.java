package lk.ijse.aad68.crop_management_system.Service;

import lk.ijse.aad68.crop_management_system.CustomOBJ.StaffMemberResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.StaffDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface StaffService {
    void saveMember(StaffDTO dto);
    void updateMember(StaffDTO dto);
    void deleteMember(String staffId);
    StaffMemberResponse getSelectedMember(String staffId);
    List<StaffDTO> getAllMembers();
}
