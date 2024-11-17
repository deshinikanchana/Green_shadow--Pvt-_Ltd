package lk.ijse.aad68.crop_management_system.Service.IMPL;

import jakarta.transaction.Transactional;
import lk.ijse.aad68.crop_management_system.CustomOBJ.MonitoringLogErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.MonitoringLogResponse;
import lk.ijse.aad68.crop_management_system.DAO.MonitoringLogDAO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.MonitoringLogDTO;
import lk.ijse.aad68.crop_management_system.Entity.MonitoringLogEntity;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.MonitoringLogNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.MonitoringLogService;
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
public class MonitoringLogServiceIMPL implements MonitoringLogService {
    @Autowired
    private final MonitoringLogDAO logDAO;
    @Autowired
    private final Mapping mapping;
    @Override
    public void saveLog(MonitoringLogDTO dto) {
        String maxIndex = AppUtil.createLogCode(logDAO.findMaxLogCode());
        if (maxIndex == null) {
            dto.setLogCode("Log-0001");
        }else{
            dto.setLogCode(maxIndex);
        }
        MonitoringLogEntity savedLog =
                logDAO.save(mapping.convertMonitoringLogDTOToEntity(dto));
        if(savedLog == null ) {
            throw new DataPersistFailedException("Cannot data saved");
        }
    }

    @Override
    public void updateLog(MonitoringLogDTO dto) {
        Optional<MonitoringLogEntity> tmpLog = logDAO.findById(dto.getLogCode());
        if(!tmpLog.isPresent()){
            throw new MonitoringLogNotFoundException("Log Details not found");
        }else {
            tmpLog.get().setObservation(dto.getObservation());
            tmpLog.get().setObservedImage(dto.getObservedImage());
            tmpLog.get().setLogFieldList(mapping.convertFieldDTOListToEntityList(dto.getLogFieldList()));
            tmpLog.get().setLogCropList(mapping.convertCropDTOListToEntityList(dto.getLogCropList()));
            tmpLog.get().setLogStaffList(mapping.convertStaffDTOListToEntityList(dto.getLogStaffList()));
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
