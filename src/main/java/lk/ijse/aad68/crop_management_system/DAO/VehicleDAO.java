package lk.ijse.aad68.crop_management_system.DAO;

import lk.ijse.aad68.crop_management_system.Entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDAO extends JpaRepository<VehicleEntity, String> {
    @Query("SELECT MAX(vehicleCode) FROM VehicleEntity")
    String findMaxVehicleCode();
}
