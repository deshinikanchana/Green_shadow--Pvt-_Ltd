package lk.ijse.aad68.crop_management_system.Controller;

import lk.ijse.aad68.crop_management_system.CustomOBJ.MonitoringLogErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.MonitoringLogResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.CropDTO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.FieldDTO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.MonitoringLogDTO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.StaffDTO;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.MonitoringLogNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.MonitoringLogService;
import lk.ijse.aad68.crop_management_system.Util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("crop_management/monitoring_logs")
@CrossOrigin("*")
public class MonitoringLogController {
    @Autowired
    private final MonitoringLogService logService;

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveLog(@RequestParam ("logCode") String logCode,
                                        @RequestParam("logDate") Date logDate,
                                        @RequestParam("observation")String observation,
                                        @RequestPart("observedImage") MultipartFile observedImage,
                                        @RequestParam("fieldIdList") List<String> fieldIdList,
                                        @RequestParam("cropIdList") List<String> cropIdList,
                                        @RequestParam("staffIdList") List<String> staffIdList) {
        MonitoringLogDTO log = new MonitoringLogDTO(logCode,logDate,observation,AppUtil.toBase64Pic(observedImage));

        if (log == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                logService.saveLog(log,fieldIdList,cropIdList,staffIdList);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping(value = "allLogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MonitoringLogDTO> getAllLogs(){
        return logService.getAllLogs();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping(value = "/{logCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitoringLogResponse getSelectedLog(@PathVariable ("logCode") String logCode)  {
        if(logCode.isEmpty() || logCode == null){
            return new MonitoringLogErrorResponse(1,"Not valid Log Code");
        }
        return logService.getSelectedLog(logCode);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_SCIENTIST')")
    @PatchMapping(value = "/{logCode}",produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateLog(
            @PathVariable ("logCode") String logCode,
            @RequestPart("updatedobservation")String updatedobservation,
            @RequestPart("updatedobservedImage") MultipartFile updatedobservedImage,
            @RequestPart("updatedFieldIdList") List<String> updatedFieldIdList,
            @RequestPart("updatedCropIdList") List<String> updatedCropIdList,
            @RequestPart("updatedStaffIdList") List<String> updatedStaffIdList

    ){
        try {
            String updateBase64Img = AppUtil.toBase64Pic(updatedobservedImage);
            var updatedLog = new MonitoringLogDTO();
            updatedLog.setLogCode(logCode);
            updatedLog.setObservation(updatedobservation);
            updatedLog.setObservedImage(updateBase64Img);


            logService.updateLog(updatedLog, updatedFieldIdList,updatedCropIdList,updatedStaffIdList);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (MonitoringLogNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_SCIENTIST')")
    @DeleteMapping(value ="/{logCode}" )
    public ResponseEntity<Void> deleteLog(@PathVariable ("logCode") String logCode) {
        try {
            logService.deleteLog(logCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (MonitoringLogNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
