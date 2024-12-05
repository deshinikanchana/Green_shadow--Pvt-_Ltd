package lk.ijse.aad68.crop_management_system.Service;

import lk.ijse.aad68.crop_management_system.CustomOBJ.CropResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.CropDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO dto,String fieldCode);
    void updateCrop(CropDTO dto,String code);
    void deleteCrop(String id);
    CropResponse getSelectedCrop(String id);
    List<CropDTO> getAllCrops();
}
