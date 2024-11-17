package lk.ijse.aad68.crop_management_system.CustomOBJ;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffMemberErrorResponse implements StaffMemberResponse, Serializable {
    private int errorCode;
    private String errorMessage;
}
