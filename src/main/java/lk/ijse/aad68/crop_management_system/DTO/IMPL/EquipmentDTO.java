package lk.ijse.aad68.crop_management_system.DTO.IMPL;
import lk.ijse.aad68.crop_management_system.CustomOBJ.EquipmentResponse;
import lk.ijse.aad68.crop_management_system.DTO.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO implements SuperDTO, EquipmentResponse {
    private String equipmentId;
    private String equipmentName;
    private String type;
    private String status;
    private StaffDTO assignedStaff;
    private FieldDTO assignedField;
}
