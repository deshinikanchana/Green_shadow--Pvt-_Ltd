package lk.ijse.aad68.crop_management_system.Entity;

import jakarta.persistence.*;
import lk.ijse.aad68.crop_management_system.ENUMS.Availability;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity implements SuperEntity {
    @Id
    private String vehicleCode;
    @Column(unique = true)
    private String licensePlateNumber;
    private String vehicleCategory;
    private String fuelType;
    @Enumerated(EnumType.STRING)
    private Availability status;
    private String remarks;
    @ManyToMany
    @JoinTable(
            name = "vehicle_staff_details",
            joinColumns = @JoinColumn(name = "vehicleCode"),
            inverseJoinColumns = @JoinColumn(name = "staffId")
    )
    private List<StaffEntity> vehicleStaffList;
}
