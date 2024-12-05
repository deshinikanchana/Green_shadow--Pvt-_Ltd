package lk.ijse.aad68.crop_management_system.Service;

import lk.ijse.aad68.crop_management_system.CustomOBJ.EquipmentResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO dto,String staffId,String fieldCode);
    void updateEquipment(EquipmentDTO dto,String staffId,String fieldCode);
    void deleteEquipment(String id);
    EquipmentResponse getSelectedEquipment(String id);
    List<EquipmentDTO> getAllEquipments();
}
