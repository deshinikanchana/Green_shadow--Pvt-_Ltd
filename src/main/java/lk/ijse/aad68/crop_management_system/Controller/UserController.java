package lk.ijse.aad68.crop_management_system.Controller;

import lk.ijse.aad68.crop_management_system.CustomOBJ.UserErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.UserResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.UserDTO;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.UserNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("crop_management/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser(@RequestBody UserDTO user) {
        if (user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                String password = passwordEncoder.encode(user.getPassword());
                user.setPassword(password);
                userService.saveUser(user);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping(value = "allUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getSelectedUser(@PathVariable ("email") String email)  {
        if(email.isEmpty() || email == null){
            return new UserErrorResponse(1,"Not valid Email");
        }
        return userService.getSelectedUser(email);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @PatchMapping(value = "/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(
            @PathVariable ("email") String email,
            @RequestPart("updatePassword") String updatePassword,
            @RequestPart ("updateRole") String updateRole
    ){
        try {
            String encodedPw = passwordEncoder.encode(updatePassword);
            var updatedUser = new UserDTO();
            updatedUser.setEmail(email);
            updatedUser.setPassword(encodedPw);
            updatedUser.setRole(updateRole);
            userService.updateUser(updatedUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @DeleteMapping(value ="/{email}" )
    public ResponseEntity<Void> deleteUser(@PathVariable ("email") String email) {
        try {
            userService.deleteUser(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
