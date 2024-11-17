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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("crop_management/monitoring_logs")
public class MonitoringLogController {
    @Autowired
    private final MonitoringLogService logService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveLog(@RequestBody MonitoringLogDTO log) {
        if (log == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                logService.saveLog(log);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "allLogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MonitoringLogDTO> getAllLogs(){
        return logService.getAllLogs();
    }

    @GetMapping(value = "/{logCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitoringLogResponse getSelectedLog(@PathVariable ("logCode") String logCode)  {
        if(logCode.isEmpty() || logCode == null){
            return new MonitoringLogErrorResponse(1,"Not valid Log Code");
        }
        return logService.getSelectedLog(logCode);
    }

    @PatchMapping(value = "/{logCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateLog(
            @PathVariable ("logCode") String id,
            @RequestPart("updateObservation") String updateObservation,
            @RequestPart ("updateImage") MultipartFile updateImage,
            @RequestPart ("updateFieldList") List<FieldDTO> updateFieldList,
            @RequestPart ("updateCropList") List<CropDTO> updateCropList,
            @RequestPart ("updateStaffList") List<StaffDTO> updateStaffList

    ){
        try {
            String updateBase64Img = AppUtil.toBase64Pic(updateImage);
            var updatedLog = new MonitoringLogDTO();
            updatedLog.setLogCode(id);
            updatedLog.setObservation(updateObservation);
            updatedLog.setObservedImage(updateBase64Img);
            updatedLog.setLogFieldList(updateFieldList);
            updatedLog.setLogCropList(updateCropList);
            updatedLog.setLogStaffList(updateStaffList);

            logService.updateLog(updatedLog);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (MonitoringLogNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
