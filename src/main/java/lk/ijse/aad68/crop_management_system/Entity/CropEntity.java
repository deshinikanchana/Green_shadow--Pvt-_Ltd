package lk.ijse.aad68.crop_management_system.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "crop")
public class CropEntity implements SuperEntity{
    @Id
    private String cropCode;
    private String commonName;
    private String scientificName;
    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;
    private String category;
    private String cropSeason;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fieldCode",nullable = false)
    private FieldEntity field;

    @ManyToMany(mappedBy = "logCropList",cascade = CascadeType.ALL)
    private List<MonitoringLogEntity> cropLogList;




}
