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
    // private List<String> cropList;
    //private List<String> staffList;
    //private List<String> fieldEquipmentsList;
    //private List<String> fieldLogList;

}




