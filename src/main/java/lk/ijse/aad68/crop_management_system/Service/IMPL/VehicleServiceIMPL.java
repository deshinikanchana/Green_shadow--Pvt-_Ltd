package lk.ijse.aad68.crop_management_system.Service.IMPL;

import jakarta.transaction.Transactional;
import lk.ijse.aad68.crop_management_system.CustomOBJ.FieldErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.VehicleErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.VehicleResponse;
import lk.ijse.aad68.crop_management_system.DAO.StaffDAO;
import lk.ijse.aad68.crop_management_system.DAO.VehicleDAO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.VehicleDTO;
import lk.ijse.aad68.crop_management_system.ENUMS.Availability;
import lk.ijse.aad68.crop_management_system.Entity.StaffEntity;
import lk.ijse.aad68.crop_management_system.Entity.VehicleEntity;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.VehicleNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.VehicleService;
import lk.ijse.aad68.crop_management_system.Util.AppUtil;
import lk.ijse.aad68.crop_management_system.Util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleServiceIMPL implements VehicleService {
    @Autowired
    private final VehicleDAO vehicleDAO;
    @Autowired
    private final StaffDAO staffDao;
    @Autowired
    private final Mapping mapping;
    @Override
    public void saveVehicle(VehicleDTO dto,List<String> staffList) {
        String maxIndex = AppUtil.createVehicleCode(vehicleDAO.findMaxVehicleCode());
        if (maxIndex == null) {
            dto.setVehicleCode("Vehicle-0001");
        }else{
            dto.setVehicleCode(maxIndex);
        }
        VehicleEntity vehicleEnt = mapping.convertVehicleDTOToEntity(dto);
        List<StaffEntity> staffEnt = new ArrayList<>();
        for (String staffId : staffList) {
            Optional<StaffEntity> optional = staffDao.findById(staffId);
            optional.ifPresent(staffEnt::add);
        }
        vehicleEnt.setVehicleStaffList(staffEnt);
        VehicleEntity savedVehicle = vehicleDAO.save(vehicleEnt);
        if(savedVehicle == null ) {
            throw new DataPersistFailedException("Cannot data saved");
        }
    }

    @Override
    public void updateVehicle(VehicleDTO dto,List<String> staff) {
        Optional<VehicleEntity> tmpVehicle = vehicleDAO.findById(dto.getVehicleCode());
        if(!tmpVehicle.isPresent()){
            throw new VehicleNotFoundException("Vehicle not found");
        }else {
            List<StaffEntity> staffEnt = new ArrayList<>();
            for (String staffId : staff) {
                Optional<StaffEntity> optional = staffDao.findById(staffId);
                optional.ifPresent(staffEnt::add);
            }

            tmpVehicle.get().setStatus(Availability.valueOf(dto.getStatus()));
            tmpVehicle.get().setRemarks(dto.getRemarks());
            tmpVehicle.get().setVehicleStaffList(staffEnt);
        }
    }

    @Override
    public void deleteVehicle(String id) {
        Optional<VehicleEntity> selectedVehicleCode = vehicleDAO.findById(id);
        if(!selectedVehicleCode.isPresent()){
            throw new VehicleNotFoundException("Vehicle not found");
        }else {
            vehicleDAO.deleteById(id);
        }
    }

    @Override
    public VehicleResponse getSelectedVehicle(String id) {
        if(vehicleDAO.existsById(id)){
            VehicleEntity vehicleEntityById = vehicleDAO.getReferenceById(id);
            return mapping.convertVehicleEntityToDTO(vehicleEntityById);
        }else {
            return new VehicleErrorResponse(0, "Vehicle not found");
        }
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        List<VehicleEntity> getAllVehicles = vehicleDAO.findAll();
        return mapping.convertVehicleEntityListToDTOList(getAllVehicles);
    }
}
