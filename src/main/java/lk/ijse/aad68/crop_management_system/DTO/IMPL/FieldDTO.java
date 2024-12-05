package lk.ijse.aad68.crop_management_system.DTO.IMPL;
import lk.ijse.aad68.crop_management_system.CustomOBJ.FieldResponse;
import lk.ijse.aad68.crop_management_system.DTO.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO implements SuperDTO , FieldResponse {
    private String fieldCode;
    private String fieldName;
    private Point fieldLocation;
    private Double fieldSize;
    private String fieldImage1;
    private String fieldImage2;
    private List<CropDTO> cropList;
    private List<StaffDTO> staffList;
    private List<EquipmentDTO> fieldEquipmentsList;
    private List<MonitoringLogDTO> fieldLogList;

}


//INSERT INTO locations (point1, point2)
//VALUES (
//        ST_GeomFromText('POINT(80.6333 7.4600)'),  -- First location: longitude 80.6333, latitude 7.4600
//ST_GeomFromText('POINT(81.6333 8.4600)')   -- Second location: longitude 81.6333, latitude 8.4600
//        );



