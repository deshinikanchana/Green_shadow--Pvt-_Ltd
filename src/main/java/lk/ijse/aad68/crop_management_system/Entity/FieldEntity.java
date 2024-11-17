package lk.ijse.aad68.crop_management_system.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "field")
public class FieldEntity implements SuperEntity{
    @Id
    private String fieldCode;
    private String fieldName;
    private Point fieldLocation;
    private Double fieldSize;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(columnDefinition = "LONGTEXT")
    private String FieldImage2;
    @OneToMany(mappedBy = "field")
    private List<CropEntity> cropList;
    @ManyToMany(mappedBy = "staffFieldList")
    private List<StaffEntity> staffList;
    @OneToMany(mappedBy = "assignedField")
    private List<EquipmentsEntity> fieldEquipmentsList;
    @ManyToMany
    @JoinTable(name = "field_log_details",
    joinColumns = @JoinColumn(name = "fieldCode"),
    inverseJoinColumns = @JoinColumn(name = "logCode"))
    private List<MonitoringLogEntity> fieldLogList;
}
