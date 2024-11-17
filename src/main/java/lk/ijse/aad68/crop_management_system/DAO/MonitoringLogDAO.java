package lk.ijse.aad68.crop_management_system.DAO;

import lk.ijse.aad68.crop_management_system.Entity.MonitoringLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringLogDAO extends JpaRepository<MonitoringLogEntity, String> {
    @Query("SELECT MAX(logCode) FROM MonitoringLogEntity")
    String findMaxLogCode();
}
