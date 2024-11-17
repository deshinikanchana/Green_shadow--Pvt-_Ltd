package lk.ijse.aad68.crop_management_system.DAO;

import lk.ijse.aad68.crop_management_system.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, String> {
}
