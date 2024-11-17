package lk.ijse.aad68.crop_management_system.Service.IMPL;

import jakarta.transaction.Transactional;
import lk.ijse.aad68.crop_management_system.CustomOBJ.FieldErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.FieldResponse;
import lk.ijse.aad68.crop_management_system.DAO.FieldDAO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.FieldDTO;
import lk.ijse.aad68.crop_management_system.Entity.FieldEntity;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.FieldNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.FieldService;
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
public class FieldServiceIMPL implements FieldService {
    @Autowired
    private final FieldDAO fieldDao;
    @Autowired
    private final Mapping mapping;
    @Override
    public void saveField(FieldDTO dto) {
        String maxIndex = AppUtil.createFieldCode(fieldDao.findMaxFieldCode());
        if (maxIndex == null) {
            dto.setFieldCode("Field-0001");
        }else{
            dto.setFieldCode(maxIndex);
        }
        FieldEntity savedField =
                fieldDao.save(mapping.convertFieldDTOToEntity(dto));
        if(savedField == null ) {
            throw new DataPersistFailedException("Cannot data saved");
        }
    }

    @Override
    public void updateField(FieldDTO dto) {
        Optional<FieldEntity> tmpField = fieldDao.findById(dto.getFieldCode());
        if(!tmpField.isPresent()){
            throw new FieldNotFoundException("Field not found");
        }else {
            tmpField.get().setFieldName(dto.getFieldName());
            tmpField.get().setFieldLocation(dto.getFieldLocation());
            tmpField.get().setFieldSize(dto.getFieldSize());
            tmpField.get().setStaffList(mapping.convertStaffDTOListToEntityList(dto.getStaffList()));
            tmpField.get().setFieldImage1(dto.getFieldImage1());
            tmpField.get().setFieldImage2(dto.getFieldImage2());
        }
    }

    @Override
    public void deleteField(String id) {
        Optional<FieldEntity> selectedFieldCode = fieldDao.findById(id);
        if(!selectedFieldCode.isPresent()){
            throw new FieldNotFoundException("Field not found");
        }else {
            fieldDao.deleteById(id);
        }
    }

    @Override
    public FieldResponse getSelectedField(String id) {
        if(fieldDao.existsById(id)){
            FieldEntity fieldEntityById = fieldDao.getReferenceById(id);
            return mapping.convertFieldEntityToDTO(fieldEntityById);
        }else {
            return new FieldErrorResponse(0, "Field not found");
        }
    }

    @Override
    public List<FieldDTO> getAllFields() {
        List<FieldEntity> getAllFields = fieldDao.findAll();
        return mapping.convertFieldEntityListToDTOList(getAllFields);
    }
}
