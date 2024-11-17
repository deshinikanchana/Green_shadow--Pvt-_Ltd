package lk.ijse.aad68.crop_management_system.Service;

import lk.ijse.aad68.crop_management_system.DTO.IMPL.UserDTO;
import lk.ijse.aad68.crop_management_system.JwtModels.JwtAuthResponse;
import lk.ijse.aad68.crop_management_system.JwtModels.SignIn;

public interface AuthenticationService {
    JwtAuthResponse signIn(SignIn signIn);
    JwtAuthResponse signUp(UserDTO signUp);
    JwtAuthResponse refreshToken(String accessToken);

}
