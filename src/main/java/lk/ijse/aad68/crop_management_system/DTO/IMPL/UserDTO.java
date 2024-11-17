package lk.ijse.aad68.crop_management_system.DTO.IMPL;
import lk.ijse.aad68.crop_management_system.CustomOBJ.UserResponse;
import lk.ijse.aad68.crop_management_system.DTO.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements SuperDTO, UserResponse {
    private String email;
    private String password;
    private String role;
}
