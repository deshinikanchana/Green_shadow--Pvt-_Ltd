package lk.ijse.aad68.crop_management_system.Service.IMPL;

import jakarta.transaction.Transactional;
import lk.ijse.aad68.crop_management_system.CustomOBJ.UserErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.UserResponse;
import lk.ijse.aad68.crop_management_system.DAO.UserDAO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.UserDTO;
import lk.ijse.aad68.crop_management_system.Entity.UserEntity;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.UserNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.UserService;
import lk.ijse.aad68.crop_management_system.Util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    @Autowired
    private final UserDAO userDao;
    @Autowired
    private final Mapping mapping;
    @Override
    public void saveUser(UserDTO userDTO) {
        userDTO.setPassword(userDTO.getPassword());
        UserEntity savedUser =
                userDao.save(mapping.convertUserDTOToEntity(userDTO));
        if(savedUser == null ) {
            throw new DataPersistFailedException("Cannot data saved");
        }
    }
    @Override
    public void updateUser(UserDTO userDTO) {
        Optional<UserEntity> tmpUser = userDao.findById(userDTO.getEmail());
        if(!tmpUser.isPresent()){
            throw new UserNotFoundException("User not found");
        }else {
            tmpUser.get().setPassword(userDTO.getPassword());
        }
    }

    @Override
    public void deleteUser(String email) {
        Optional<UserEntity> selecteEmail = userDao.findById(email);
        if(!selecteEmail.isPresent()){
            throw new UserNotFoundException("User not found");
        }else {
            userDao.deleteById(email);
        }
    }

    @Override
    public UserResponse getSelectedUser(String email) {
        if(userDao.existsById(email)){
            UserEntity userEntityByEmail = userDao.getReferenceById(email);
            return mapping.convertUserEntityToDTO(userEntityByEmail);
        }else {
            return new UserErrorResponse(0, "User not found");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> getAllUsers = userDao.findAll();
        return mapping.convertUserEntityListToDTO(getAllUsers);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return email ->
                userDao.findById(email)
                        .orElseThrow(()-> new UserNotFoundException("User Not found"));
    }
}
