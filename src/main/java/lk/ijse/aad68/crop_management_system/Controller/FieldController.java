package lk.ijse.aad68.crop_management_system.Controller;

import lk.ijse.aad68.crop_management_system.DTO.IMPL.FieldDTO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.StaffDTO;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.FieldNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.FieldService;
import lk.ijse.aad68.crop_management_system.Util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("crop_management/fields")
@RequiredArgsConstructor
public class FieldController {
    @Autowired
    private final FieldService fieldService;

    private static final Logger logger = LoggerFactory.getLogger(FieldController.class);
    @PreAuthorize("hasRole('ROLE_SCIENTIST') or hasRole('ROLE_MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveField(@RequestBody FieldDTO field){
        logger.info("y"+field.getFieldLocation().getY() +"x"+field.getFieldLocation().getX());
        field.setFieldLocation(new org.springframework.data.geo.Point(field.getFieldLocation().getX(), field.getFieldLocation().getY()));
        logger.info("Request received to save a new field: {}", field);
        try {
            fieldService.saveField(field);
            logger.info("Field saved successfully: {}", field);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to save field: {}", field, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error while saving field: {}", field, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_SCIENTIST') or hasRole('ROLE_MANAGER')")
    @PatchMapping(value = "/{fieldCode}", params = "staffIds")
    public ResponseEntity<?> updateField(
            @PathVariable("fieldCode") String fieldCode,
            @RequestPart("updateFieldName") String updateFieldName,
            @RequestPart("updateLocation") Point updateLocation,
            @RequestPart("updateSize") double updateSize,
            @RequestPart("updateStaffList") List<StaffDTO> updateStaffList,
            @RequestPart("updateImage1") MultipartFile updateImage1,
            @RequestPart("updateImage2") MultipartFile updateImage2

    ) {
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setFieldCode(fieldCode);
        fieldDTO.setFieldName(updateFieldName);
        fieldDTO.setFieldLocation(new Point(updateLocation.getX(), updateLocation.getY()));
        fieldDTO.setFieldSize(updateSize);
        fieldDTO.setStaffList(updateStaffList);
        fieldDTO.setFieldImage1(AppUtil.toBase64Pic(updateImage1));
        fieldDTO.setFieldImage2(AppUtil.toBase64Pic(updateImage2));
        logger.info("Request received to update field with staff IDs {}: {}", updateStaffList, fieldDTO);
        try {
            fieldService.updateField(fieldDTO);
            logger.info("Field updated successfully: {}", fieldDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FieldNotFoundException e) {
            logger.error("Field not found for update: {}", fieldDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to update field: {}", fieldDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Internal server error while updating field: {}", fieldDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping("/{fieldCode}")
    public ResponseEntity<?> getField(@PathVariable String fieldCode){
        logger.info("Request received to get field with code: {}", fieldCode);
        try {
            var field = fieldService.getSelectedField(fieldCode);
            logger.info("Field retrieved successfully: {}", field);
            return new ResponseEntity<>(field, HttpStatus.OK);
        } catch (FieldNotFoundException e) {
            logger.error("Field not found with code: {}", fieldCode, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error while retrieving field with code: {}", fieldCode, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_SCIENTIST') or hasRole('ROLE_MANAGER')")
    @DeleteMapping("/{fieldCode}")
    public ResponseEntity<?> deleteField(@PathVariable String fieldCode){
        logger.info("Request received to delete field with code: {}", fieldCode);
        try {
            fieldService.deleteField(fieldCode);
            logger.info("Field deleted successfully with code: {}", fieldCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FieldNotFoundException e) {
            logger.error("Field not found for deletion with code: {}", fieldCode, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error while deleting field with code: {}", fieldCode, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping("allFields")
    public ResponseEntity<?> getAllFields(){
        logger.info("Request received to get all fields");
        try {
            var fields = fieldService.getAllFields();
            logger.info("All fields retrieved successfully");
            return new ResponseEntity<>(fields, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal server error while retrieving all fields", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
