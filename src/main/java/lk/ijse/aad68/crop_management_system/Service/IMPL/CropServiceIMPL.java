package lk.ijse.aad68.crop_management_system.Service.IMPL;

import jakarta.transaction.Transactional;
import lk.ijse.aad68.crop_management_system.CustomOBJ.CropErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.CropResponse;
import lk.ijse.aad68.crop_management_system.DAO.CropDAO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.CropDTO;
import lk.ijse.aad68.crop_management_system.Entity.CropEntity;
import lk.ijse.aad68.crop_management_system.Exception.CropNotFoundException;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Service.CropService;
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
public class CropServiceIMPL implements CropService {
    @Autowired
    private final CropDAO cropDAO;
    @Autowired
    private final Mapping mapping;
    @Override
    public void saveCrop(CropDTO dto) {
        String maxIndex = AppUtil.createCropCode(cropDAO.findMaxCropCode());
        if (maxIndex == null) {
            dto.setCropCode("Crop-0001");
        }else{
            dto.setCropCode(maxIndex);
        }
        CropEntity savedCrop =
                cropDAO.save(mapping.convertCropDTOToEntity(dto));
        if(savedCrop == null ) {
            throw new DataPersistFailedException("Cannot data saved");
        }
    }

    @Override
    public void updateCrop(CropDTO dto) {
        Optional<CropEntity> tmpCrop = cropDAO.findById(dto.getCropCode());
        if(!tmpCrop.isPresent()){
            throw new CropNotFoundException("Crop not found");
        }else {
            tmpCrop.get().setCommonName(dto.getCommonName());
            tmpCrop.get().setScientificName(dto.getScientificName());
            tmpCrop.get().setCropSeason(dto.getCropSeason());
            tmpCrop.get().setCategory(dto.getCategory());
            tmpCrop.get().setField(mapping.convertFieldDTOToEntity(dto.getField()));
            tmpCrop.get().setCropImage(dto.getCropImage());
        }
    }

    @Override
    public void deleteCrop(String id) {
        Optional<CropEntity> selectedCropCode = cropDAO.findById(id);
        if(!selectedCropCode.isPresent()){
            throw new CropNotFoundException("Crop not found");
        }else {
            cropDAO.deleteById(id);
        }
    }

    @Override
    public CropResponse getSelectedCrop(String id) {
        if(cropDAO.existsById(id)){
            CropEntity cropEntityById = cropDAO.getReferenceById(id);
            return mapping.convertCropEntityToDTO(cropEntityById);
        }else {
            return new CropErrorResponse(0, "Crop not found");
        }
    }

    @Override
    public List<CropDTO> getAllCrops() {
        List<CropEntity> getAllCrops = cropDAO.findAll();
        return mapping.convertCropEntityListToDTOList(getAllCrops);
    }
}
