package lk.ijse.aad68.crop_management_system.Entity;

import jakarta.persistence.*;
import lk.ijse.aad68.crop_management_system.ENUMS.Availability;
import lk.ijse.aad68.crop_management_system.ENUMS.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class EquipmentsEntity implements SuperEntity{
    @Id
    private String equipmentId;
    private String equipmentName;
    @Enumerated(EnumType.STRING)
    private EquipmentType type;
    @Enumerated(EnumType.STRING)
    private Availability status;
    @ManyToOne
    @JoinColumn(name = "staffId",nullable = false)
    private StaffEntity assignedStaff;
    @ManyToOne
    @JoinColumn(name = "fieldCode",nullable = false)
    private FieldEntity assignedField;
}
