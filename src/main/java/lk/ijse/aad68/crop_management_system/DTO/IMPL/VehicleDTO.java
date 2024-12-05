package lk.ijse.aad68.crop_management_system.DTO.IMPL;
import lk.ijse.aad68.crop_management_system.CustomOBJ.VehicleResponse;
import lk.ijse.aad68.crop_management_system.DTO.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO implements SuperDTO, VehicleResponse {
    private String vehicleCode;
    private String licensePlateNumber;
    private String vehicleCategory;
    private String fuelType;
    private String status;
    private String remarks;
    //private List<String> vehicleStaffList;
}
