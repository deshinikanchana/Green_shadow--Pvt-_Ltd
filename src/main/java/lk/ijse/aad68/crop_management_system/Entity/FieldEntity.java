package lk.ijse.aad68.crop_management_system.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String fieldImage2;
    @OneToMany(mappedBy = "field",fetch = FetchType.EAGER)
    private List<CropEntity> cropList;

    @ManyToMany
    @JoinTable(name = "staff_field_details",
    joinColumns = @JoinColumn(name = "fieldCode"),
    inverseJoinColumns= @JoinColumn(name = "staffId"))
    @JsonManagedReference
    private List<StaffEntity> staffList;

    @OneToMany(mappedBy = "assignedField")
    private List<EquipmentsEntity> fieldEquipmentsList;

    @ManyToMany(mappedBy = "logFieldList")
    private List<MonitoringLogEntity> fieldLogList;

}
