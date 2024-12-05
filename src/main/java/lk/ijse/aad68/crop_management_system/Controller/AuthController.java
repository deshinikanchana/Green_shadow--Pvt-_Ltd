package lk.ijse.aad68.crop_management_system.Controller;

import lk.ijse.aad68.crop_management_system.DTO.IMPL.UserDTO;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.JwtModels.JwtAuthResponse;
import lk.ijse.aad68.crop_management_system.JwtModels.SignIn;
import lk.ijse.aad68.crop_management_system.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("crop_management/Auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @PostMapping(value = "signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JwtAuthResponse> signUp(@RequestPart("email") String email,
                                                  @RequestPart("password") String password,
                                                  @RequestPart("role") String role) {
        logger.info("SignUp request received for email: {}", email);
        try {
            UserDTO buildUserDTO = new UserDTO();
            buildUserDTO.setEmail(email);
            buildUserDTO.setPassword(passwordEncoder.encode(password));
            buildUserDTO.setRole(role);
            JwtAuthResponse jwtAuthResponse = authenticationService.signUp(buildUserDTO);
            if (jwtAuthResponse != null) {
                logger.info("User signed up successfully: {}", buildUserDTO.getEmail());
                return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
            } else {
                logger.warn("SignUp failed for email: {}", buildUserDTO.getEmail());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (DataPersistFailedException e) {
            logger.error("Failed to persist user with email: {}",email, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error during signup for email: {}",email, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "signin")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignIn signIn) {
        logger.info("SignIn request received for email: {}", signIn.getEmail());
        try {
            JwtAuthResponse jwtAuthResponse = authenticationService.signIn(signIn);
            logger.info("User signed in successfully: {}", signIn.getEmail());
            return ResponseEntity.ok(jwtAuthResponse);
        } catch (Exception e) {
            logger.error("SignIn failed for email: {}", signIn.getEmail(), e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        logger.info("Refresh token request received for token: {}", refreshToken);
        try {
            JwtAuthResponse jwtAuthResponse = authenticationService.refreshToken(refreshToken);
            logger.info("Refresh token issued successfully for token: {}", refreshToken);
            return ResponseEntity.ok(jwtAuthResponse);
        } catch (Exception e) {
            logger.error("Failed to refresh token for token: {}", refreshToken, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
