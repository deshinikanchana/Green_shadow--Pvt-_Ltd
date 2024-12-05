package lk.ijse.aad68.crop_management_system.Service.IMPL;

import jakarta.transaction.Transactional;
import lk.ijse.aad68.crop_management_system.CustomOBJ.EquipmentErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.EquipmentResponse;
import lk.ijse.aad68.crop_management_system.DAO.EquipmentDAO;
import lk.ijse.aad68.crop_management_system.DAO.FieldDAO;
import lk.ijse.aad68.crop_management_system.DAO.StaffDAO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.EquipmentDTO;
import lk.ijse.aad68.crop_management_system.ENUMS.Availability;
import lk.ijse.aad68.crop_management_system.ENUMS.EquipmentType;
import lk.ijse.aad68.crop_management_system.Entity.EquipmentsEntity;
import lk.ijse.aad68.crop_management_system.Entity.FieldEntity;
import lk.ijse.aad68.crop_management_system.Entity.StaffEntity;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.EquipmentNotFoundException;
import lk.ijse.aad68.crop_management_system.Exception.FieldNotFoundException;
import lk.ijse.aad68.crop_management_system.Exception.StaffMemberNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.EquipmentService;
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
public class EquipmentServiceIMPL implements EquipmentService {
    @Autowired
    private final EquipmentDAO equipmentDAO;
    @Autowired
    private final FieldDAO fieldDAO;
    @Autowired
    private final StaffDAO staffDAO;
    @Autowired
    private final Mapping mapping;
    @Override
    public void saveEquipment(EquipmentDTO dto,String staffId,String fieldCode) {
        String maxIndex = AppUtil.createEquipmentId(equipmentDAO.findMaxEquipmentId());
        if (maxIndex == null) {
            dto.setEquipmentId("Eq-0001");
        }else{
            dto.setEquipmentId(maxIndex);
        }
        EquipmentsEntity equipmentsEntity = mapping.convertEquipmentDTOToEntity(dto);

        FieldEntity field = fieldDAO.findById(fieldCode).orElseThrow(
                () -> new FieldNotFoundException("Field not found")
        );

        StaffEntity staff = staffDAO.findById(staffId).orElseThrow(
                () -> new StaffMemberNotFoundException("Staff Member not found")
        );

        equipmentsEntity.setAssignedField(field);
        equipmentsEntity.setAssignedStaff(staff);

        EquipmentsEntity savedEquipment = equipmentDAO.save(equipmentsEntity);

        if(savedEquipment == null ) {
            throw new DataPersistFailedException("Cannot data saved");
        }
    }

    @Override
    public void updateEquipment(EquipmentDTO dto,String staffId,String fieldCode) {
        Optional<EquipmentsEntity> tmpEquipment = equipmentDAO.findById(dto.getEquipmentId());
        if(!tmpEquipment.isPresent()){
            throw new EquipmentNotFoundException("Equipment not found");
        }else {
            FieldEntity field = fieldDAO.findById(fieldCode).orElseThrow(
                    () -> new FieldNotFoundException("Field not found")
            );

            StaffEntity staff = staffDAO.findById(staffId).orElseThrow(
                    () -> new StaffMemberNotFoundException("Staff Member not found")
            );
            tmpEquipment.get().setEquipmentName(dto.getEquipmentName());
            tmpEquipment.get().setType(EquipmentType.valueOf(dto.getType()));
            tmpEquipment.get().setStatus(Availability.valueOf(dto.getStatus()));
            tmpEquipment.get().setAssignedStaff(staff);
           tmpEquipment.get().setAssignedField(field);
        }
    }

    @Override
    public void deleteEquipment(String id) {
        Optional<EquipmentsEntity> selectedEquipmentId = equipmentDAO.findById(id);
        if(!selectedEquipmentId.isPresent()){
            throw new EquipmentNotFoundException("Equipment not found");
        }else {
            equipmentDAO.deleteById(id);
        }
    }

    @Override
    public EquipmentResponse getSelectedEquipment(String id) {
        if(equipmentDAO.existsById(id)){
            EquipmentsEntity equipmentEntityById = equipmentDAO.getReferenceById(id);
            return mapping.convertEquipmentEntityToDTO(equipmentEntityById);
        }else {
            return new EquipmentErrorResponse(0, "Equipment not found");
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquipments() {
        List<EquipmentsEntity> getAllEquipments = equipmentDAO.findAll();
        return mapping.convertEquipmentEntityListToDTOList(getAllEquipments);
    }
}
