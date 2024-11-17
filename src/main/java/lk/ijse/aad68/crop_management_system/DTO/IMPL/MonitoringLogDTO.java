package lk.ijse.aad68.crop_management_system.DTO.IMPL;

import lk.ijse.aad68.crop_management_system.CustomOBJ.MonitoringLogResponse;
import lk.ijse.aad68.crop_management_system.DTO.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitoringLogDTO implements SuperDTO, MonitoringLogResponse {
    private String logCode;
    private Date date;
    private String observation;
    private String observedImage;
    private List<FieldDTO> logFieldList;
    private List<CropDTO> logCropList;
    private List<StaffDTO> logStaffList;
}
