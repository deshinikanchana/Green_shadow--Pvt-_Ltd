package lk.ijse.aad68.crop_management_system.DAO;

import lk.ijse.aad68.crop_management_system.Entity.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CropDAO extends JpaRepository<CropEntity, String> {
    @Query("SELECT MAX(cropCode) FROM CropEntity")
    String findMaxCropCode();
}
