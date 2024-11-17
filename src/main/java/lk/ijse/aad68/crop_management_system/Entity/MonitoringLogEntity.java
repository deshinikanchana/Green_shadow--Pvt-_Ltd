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
    @ManyToMany(mappedBy = "fieldLogList")
    private List<FieldEntity> logFieldList;
    @ManyToMany(mappedBy = "cropLogList")
    private List<CropEntity> logCropList;
    @ManyToMany(mappedBy = "staffLogList")
    private List<StaffEntity> logStaffList;
}
