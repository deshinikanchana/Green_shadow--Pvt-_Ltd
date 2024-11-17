package lk.ijse.aad68.crop_management_system.DTO.IMPL;

import lk.ijse.aad68.crop_management_system.CustomOBJ.StaffMemberResponse;
import lk.ijse.aad68.crop_management_system.DTO.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO implements SuperDTO , StaffMemberResponse {
    private String staffId;
    private String firstName;
    private String lastName;
    private String designation;
    private String gender;
    private Date joinedDate;
    private Date DOB;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String contactNo;
    private String email;
    private String role;
    private List<EquipmentDTO> staffEquipmentList;
    private List<VehicleDTO> staffVehicleList;
    private List<FieldDTO> staffFieldList;
    private List<MonitoringLogDTO> staffLogList;
}

