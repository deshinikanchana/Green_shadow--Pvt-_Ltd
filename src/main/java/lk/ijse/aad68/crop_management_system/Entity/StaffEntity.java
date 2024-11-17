package lk.ijse.aad68.crop_management_system.Entity;

import jakarta.persistence.*;
import lk.ijse.aad68.crop_management_system.ENUMS.Designation;
import lk.ijse.aad68.crop_management_system.ENUMS.Gender;
import lk.ijse.aad68.crop_management_system.ENUMS.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.sql.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class StaffEntity implements SuperEntity {
    @Id
    private String staffId;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Designation designation;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date joinedDate;
    private Date DOB;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String contactNo;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "assignedStaff")
    private List<EquipmentsEntity> staffEquipmentList;
    @ManyToMany
    @JoinTable(
            name = "vehicle_staff_details",
            joinColumns = @JoinColumn(name = "staffId"),
            inverseJoinColumns = @JoinColumn(name = "vehicleCode")
    )
    private List<VehicleEntity> staffVehicleList;
    @ManyToMany
    @JoinTable(
            name = "staff_field_details",
            joinColumns = @JoinColumn(name = "staffId"),
            inverseJoinColumns = @JoinColumn(name = "fieldCode")
    )
    private List<FieldEntity> staffFieldList;
    @ManyToMany
    @JoinTable(
            name = "staff_log_details",
            joinColumns = @JoinColumn(name = "staffId"),
            inverseJoinColumns = @JoinColumn(name = "logCode")
    )
    private List<MonitoringLogEntity> staffLogList;
}
