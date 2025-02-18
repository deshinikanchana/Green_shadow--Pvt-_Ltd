package lk.ijse.aad68.crop_management_system.Service;

import lk.ijse.aad68.crop_management_system.CustomOBJ.VehicleResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.VehicleDTO;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO dto,List<String> staffList);
    void updateVehicle(VehicleDTO dto,List<String> staffList);
    void deleteVehicle(String id);
    VehicleResponse getSelectedVehicle(String id);
    List<VehicleDTO> getAllVehicles();
}
