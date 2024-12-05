package lk.ijse.aad68.crop_management_system.Service;

import lk.ijse.aad68.crop_management_system.CustomOBJ.MonitoringLogResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.MonitoringLogDTO;

import java.util.List;

public interface MonitoringLogService {
    void saveLog(MonitoringLogDTO dto,List<String> fieldList,List<String> cropList,List<String> staffList);
    void updateLog(MonitoringLogDTO dto,List<String> fieldList,List<String> cropList,List<String> staffList);
    void deleteLog(String id);
    MonitoringLogResponse getSelectedLog(String id);
    List<MonitoringLogDTO> getAllLogs();
}
