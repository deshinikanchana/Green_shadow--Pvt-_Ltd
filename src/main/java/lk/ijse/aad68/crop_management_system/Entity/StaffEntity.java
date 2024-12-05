package lk.ijse.aad68.crop_management_system.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @OneToMany(mappedBy = "assignedStaff",fetch = FetchType.EAGER)
    private List<EquipmentsEntity> staffEquipmentList;

    @ManyToMany(mappedBy = "vehicleStaffList",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VehicleEntity> staffVehicleList;

    @ManyToMany(mappedBy = "staffList",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<FieldEntity> staffFieldList;

    @ManyToMany(mappedBy = "logStaffList",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MonitoringLogEntity> staffLogList;
}
