package lk.ijse.aad68.crop_management_system.DAO;

import lk.ijse.aad68.crop_management_system.Entity.EquipmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentDAO extends JpaRepository<EquipmentsEntity, String> {
    @Query("SELECT MAX(equipmentId) FROM EquipmentsEntity ")
    String findMaxEquipmentId();
}
