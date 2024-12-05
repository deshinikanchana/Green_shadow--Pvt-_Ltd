package lk.ijse.aad68.crop_management_system.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "monitoringLog")
public class MonitoringLogEntity implements SuperEntity{
    @Id
    private String logCode;
    private Date date;
    private String observation;
    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;
    @ManyToMany()
    @JoinTable(name = "field_log_details",
            joinColumns = @JoinColumn(name = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "fieldCode"))
    private List<FieldEntity> logFieldList;

    @ManyToMany()
    @JoinTable(
            name = "crop_log_details",
            joinColumns = @JoinColumn(name = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "cropCode")
    )
    private List<CropEntity> logCropList;

    @ManyToMany()
    @JoinTable(
            name = "staff_log_details",
            joinColumns = @JoinColumn(name = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "staffId")
    )
    private List<StaffEntity> logStaffList;
}
