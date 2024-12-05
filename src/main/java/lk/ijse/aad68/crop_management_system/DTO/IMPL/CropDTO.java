package lk.ijse.aad68.crop_management_system.DTO.IMPL;

import lk.ijse.aad68.crop_management_system.CustomOBJ.CropResponse;
import lk.ijse.aad68.crop_management_system.DTO.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDTO implements SuperDTO , CropResponse {

    private String cropCode;
    private String commonName;
    private String scientificName;
    private String cropImage;
    private String category;
    private String cropSeason;
    private String field;
    //private List<String> cropLogList;
}
