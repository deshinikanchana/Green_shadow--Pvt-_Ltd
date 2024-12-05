package lk.ijse.aad68.crop_management_system.Service.IMPL;

import jakarta.transaction.Transactional;
import lk.ijse.aad68.crop_management_system.CustomOBJ.MonitoringLogErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.MonitoringLogResponse;
import lk.ijse.aad68.crop_management_system.DAO.CropDAO;
import lk.ijse.aad68.crop_management_system.DAO.FieldDAO;
import lk.ijse.aad68.crop_management_system.DAO.MonitoringLogDAO;
import lk.ijse.aad68.crop_management_system.DAO.StaffDAO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.MonitoringLogDTO;
import lk.ijse.aad68.crop_management_system.Entity.CropEntity;
import lk.ijse.aad68.crop_management_system.Entity.FieldEntity;
import lk.ijse.aad68.crop_management_system.Entity.MonitoringLogEntity;
import lk.ijse.aad68.crop_management_system.Entity.StaffEntity;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.MonitoringLogNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.MonitoringLogService;
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
public class MonitoringLogServiceIMPL implements MonitoringLogService {
    @Autowired
    private final MonitoringLogDAO logDAO;
    @Autowired
    private final StaffDAO staffDao;
    @Autowired
    private final CropDAO cropDao;
    @Autowired
    private final FieldDAO fieldDao;
    @Autowired
    private final Mapping mapping;
    @Override
    public void saveLog(MonitoringLogDTO dto,List<String> fieldList,List<String> cropList,List<String> staffList) {
        String maxIndex = AppUtil.createLogCode(logDAO.findMaxLogCode());
        if (maxIndex == null) {
            dto.setLogCode("Log-0001");
        }else{
            dto.setLogCode(maxIndex);
        }
        MonitoringLogEntity logEntity = mapping.convertMonitoringLogDTOToEntity(dto);

        List<StaffEntity> staffEnt = new ArrayList<>();
        for (String staffId : staffList) {
            Optional<StaffEntity> optional = staffDao.findById(staffId);
            optional.ifPresent(staffEnt::add);
        }

        List<FieldEntity> fieldEnt = new ArrayList<>();
        for (String fieldCode : fieldList) {
            Optional<FieldEntity> optional = fieldDao.findById(fieldCode);
            optional.ifPresent(fieldEnt::add);
        }

        List<CropEntity> cropEnt = new ArrayList<>();
        for (String cropCode : cropList) {
            Optional<CropEntity> optional = cropDao.findById(cropCode);
            optional.ifPresent(cropEnt::add);
        }
        logEntity.setLogStaffList(staffEnt);
        logEntity.setLogFieldList(fieldEnt);
        logEntity.setLogCropList(cropEnt);

        MonitoringLogEntity savedLog = logDAO.save(logEntity);
        if(savedLog == null ) {
            throw new DataPersistFailedException("Cannot data saved");
        }
    }

    @Override
    public void updateLog(MonitoringLogDTO dto,List<String> fieldList,List<String> cropList,List<String> staffList) {
        Optional<MonitoringLogEntity> tmpLog = logDAO.findById(dto.getLogCode());
        if(!tmpLog.isPresent()){
            throw new MonitoringLogNotFoundException("Log Details not found");
        }else {
            List<StaffEntity> staffEnt = new ArrayList<>();
            for (String staffId : staffList) {
                Optional<StaffEntity> optional = staffDao.findById(staffId);
                optional.ifPresent(staffEnt::add);
            }

            List<FieldEntity> fieldEnt = new ArrayList<>();
            for (String fieldCode : fieldList) {
                Optional<FieldEntity> optional = fieldDao.findById(fieldCode);
                optional.ifPresent(fieldEnt::add);
            }

            List<CropEntity> cropEnt = new ArrayList<>();
            for (String cropCode : cropList) {
                Optional<CropEntity> optional = cropDao.findById(cropCode);
                optional.ifPresent(cropEnt::add);
            }

            tmpLog.get().setObservation(dto.getObservation());
            tmpLog.get().setObservedImage(dto.getObservedImage());
            tmpLog.get().setLogFieldList(fieldEnt);
            tmpLog.get().setLogCropList(cropEnt);
            tmpLog.get().setLogStaffList(staffEnt);
        }
    }

    @Override
    public void deleteLog(String id) {
        Optional<MonitoringLogEntity> selectedLogCode = logDAO.findById(id);
        if(!selectedLogCode.isPresent()){
            throw new MonitoringLogNotFoundException("Log Details not found");
        }else {
            logDAO.deleteById(id);
        }
    }

    @Override
    public MonitoringLogResponse getSelectedLog(String id) {
        if(logDAO.existsById(id)){
            MonitoringLogEntity logEntityById = logDAO.getReferenceById(id);
            return mapping.convertMonitoringLogEntityToDTO(logEntityById);
        }else {
            return new MonitoringLogErrorResponse(0, "Log Details not found");
        }
    }

    @Override
    public List<MonitoringLogDTO> getAllLogs() {
        List<MonitoringLogEntity> getAllLogs = logDAO.findAll();
        return mapping.convertMonitoringLogEntityListToDTOList(getAllLogs);
    }
}
