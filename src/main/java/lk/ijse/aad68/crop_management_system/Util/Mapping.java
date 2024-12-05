package lk.ijse.aad68.crop_management_system.Util;

import lk.ijse.aad68.crop_management_system.DTO.IMPL.*;
import lk.ijse.aad68.crop_management_system.Entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapping {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertUserEntityToDTO(UserEntity user) {
        return modelMapper.map(user, UserDTO.class);
    }
    public UserEntity convertUserDTOToEntity(UserDTO dto) {
        return modelMapper.map(dto, UserEntity.class);
    }
    public List<UserDTO> convertUserEntityListToDTO(List<UserEntity> users) {
        return modelMapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());
    }

    public FieldEntity convertFieldDTOToEntity(FieldDTO dto) {
       return modelMapper.map(dto, FieldEntity.class);
    }
    public FieldDTO convertFieldEntityToDTO(FieldEntity entity) {
        return modelMapper.map(entity, FieldDTO.class);
    }
    public List<FieldDTO> convertFieldEntityListToDTOList(List<FieldEntity> entityList) {
        return modelMapper.map(entityList, new TypeToken<List<FieldDTO>>() {}.getType());
    }
    public List<FieldEntity> convertFieldDTOListToEntityList(List<FieldDTO> DTOList) {
        return modelMapper.map(DTOList, new TypeToken<List<FieldEntity>>() {}.getType());
    }

    public CropEntity convertCropDTOToEntity(CropDTO dto) {
        return modelMapper.map(dto, CropEntity.class);
    }
    public CropDTO convertCropEntityToDTO(CropEntity entity) {
        return modelMapper.map(entity, CropDTO.class);
    }
    public List<CropDTO> convertCropEntityListToDTOList(List<CropEntity> entityList) {
        return modelMapper.map(entityList, new TypeToken<List<CropDTO>>() {}.getType());
    }
    public List<CropEntity> convertCropDTOListToEntityList(List<CropDTO> DTOList) {
        return modelMapper.map(DTOList, new TypeToken<List<CropEntity>>() {}.getType());
    }



    public EquipmentsEntity convertEquipmentDTOToEntity(EquipmentDTO dto) {
        return modelMapper.map(dto, EquipmentsEntity.class);
    }
    public EquipmentDTO convertEquipmentEntityToDTO(EquipmentsEntity entity) {
        return modelMapper.map(entity, EquipmentDTO.class);
    }
    public List<EquipmentDTO> convertEquipmentEntityListToDTOList(List<EquipmentsEntity> entityList) {
        return modelMapper.map(entityList, new TypeToken<List<EquipmentDTO>>() {}.getType());
    }

    public MonitoringLogEntity convertMonitoringLogDTOToEntity(MonitoringLogDTO dto) {
        return modelMapper.map(dto, MonitoringLogEntity.class);
    }
    public MonitoringLogDTO convertMonitoringLogEntityToDTO(MonitoringLogEntity entity) {
        return modelMapper.map(entity, MonitoringLogDTO.class);
    }
    public List<MonitoringLogDTO> convertMonitoringLogEntityListToDTOList(List<MonitoringLogEntity> entityList) {
        return modelMapper.map(entityList, new TypeToken<List<MonitoringLogDTO>>() {}.getType());
    }

    public StaffEntity convertStaffDTOToEntity(StaffDTO dto) {
        return modelMapper.map(dto, StaffEntity.class);
    }
    public StaffDTO convertStaffEntityToDTO(StaffEntity entity) {
        return modelMapper.map(entity, StaffDTO.class);
    }
    public List<StaffDTO> convertStaffEntityListToDTOList(List<StaffEntity> entityList) {
        return modelMapper.map(entityList, new TypeToken<List<StaffDTO>>() {}.getType());
    }
    public List<StaffEntity> convertStaffDTOListToEntityList(List<StaffDTO> DTOList) {
        return modelMapper.map(DTOList, new TypeToken<List<StaffEntity>>() {}.getType());
    }

    public VehicleEntity convertVehicleDTOToEntity(VehicleDTO dto) {
        return modelMapper.map(dto, VehicleEntity.class);
    }
    public VehicleDTO convertVehicleEntityToDTO(VehicleEntity entity) {
        return modelMapper.map(entity, VehicleDTO.class);
    }
    public List<VehicleDTO> convertVehicleEntityListToDTOList(List<VehicleEntity> entityList) {
        return modelMapper.map(entityList, new TypeToken<List<VehicleDTO>>() {}.getType());
    }
}

