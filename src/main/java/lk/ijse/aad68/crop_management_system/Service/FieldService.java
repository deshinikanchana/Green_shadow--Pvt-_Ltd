package lk.ijse.aad68.crop_management_system.Service;

import lk.ijse.aad68.crop_management_system.CustomOBJ.FieldResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.FieldDTO;

import java.util.List;

public interface FieldService {
    void saveField(FieldDTO dto);
    void updateField(FieldDTO dto);
    void deleteField(String id);
    FieldResponse getSelectedField(String id);
    List<FieldDTO> getAllFields();
}
