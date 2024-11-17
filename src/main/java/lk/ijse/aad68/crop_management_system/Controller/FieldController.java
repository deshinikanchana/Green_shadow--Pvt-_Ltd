package lk.ijse.aad68.crop_management_system.Controller;

import lk.ijse.aad68.crop_management_system.CustomOBJ.FieldErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.FieldResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.FieldDTO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.StaffDTO;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.FieldNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.FieldService;
import lk.ijse.aad68.crop_management_system.Util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("crop_management/fields")
@RequiredArgsConstructor
public class FieldController {
    @Autowired
    private final FieldService fieldService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveField(@RequestBody FieldDTO field) {
        if (field == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                fieldService.saveField(field);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

   // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "allFields", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields(){
        return fieldService.getAllFields();
    }

    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldResponse getSelectedField(@PathVariable ("fieldCode") String fieldCode)  {
        if(fieldCode.isEmpty() || fieldCode == null){
            return new FieldErrorResponse(1,"Not valid Field Code");
        }
        return fieldService.getSelectedField(fieldCode);
    }

    @PatchMapping(value = "/{fieldCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateField(
            @PathVariable ("fieldCode") String id,
            @RequestPart("updateFieldName") String updateFieldName,
            @RequestPart ("updateLocation") Point updateLocation,
            @RequestPart ("updateSize") Double updateSize,
            @RequestPart ("updateStaffList") List<StaffDTO> updateStaffList,
            @RequestPart ("updateImage1") MultipartFile updateImage1,
            @RequestPart ("updateImage2") MultipartFile updateImage2
    ){
        try {
            String updateBase64img1 = AppUtil.toBase64Pic(updateImage1);
            String updateBase64img2 = AppUtil.toBase64Pic(updateImage2);
            var updatedField = new FieldDTO();
            updatedField.setFieldName(updateFieldName);
            updatedField.setFieldLocation(updateLocation);
            updatedField.setFieldSize(updateSize);
            updatedField.setStaffList(updateStaffList);
            updatedField.setFieldImage1(updateBase64img1);
            updatedField.setFieldImage2(updateBase64img2);
            fieldService.updateField(updatedField);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (FieldNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value ="/{fieldCode}" )
    public ResponseEntity<Void> deleteField(@PathVariable ("fieldCode") String fieldCode) {
        try {
            fieldService.deleteField(fieldCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (FieldNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
