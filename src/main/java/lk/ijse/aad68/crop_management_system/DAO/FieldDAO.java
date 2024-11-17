package lk.ijse.aad68.crop_management_system.DAO;

import lk.ijse.aad68.crop_management_system.Entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDAO extends JpaRepository<FieldEntity, String> {
    @Query("SELECT MAX(fieldCode) FROM FieldEntity")
    String findMaxFieldCode();
}
