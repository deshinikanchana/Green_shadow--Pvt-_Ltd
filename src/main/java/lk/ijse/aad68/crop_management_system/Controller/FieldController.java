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
@CrossOrigin("*")
public class FieldController {
    @Autowired
    private final FieldService fieldService;

    private static final Logger logger = LoggerFactory.getLogger(FieldController.class);
    @PreAuthorize("hasRole('ROLE_SCIENTIST') or hasRole('ROLE_MANAGER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(@RequestPart("fieldCode") String fieldCode,
                                          @RequestPart("fieldName") String fieldName,
                                          @RequestPart("fieldLocation") Point fieldLocation,
                                          @RequestPart("fieldSize") Double fieldSize,
                                          @RequestPart("staffList") List<String> staffList,
                                          @RequestPart("fieldImage1") MultipartFile fieldImage1,
                                          @RequestPart("fieldImage2") MultipartFile fieldImage2


    ){
        logger.info("y"+fieldLocation.getY() +"x"+fieldLocation.getX());

        FieldDTO field = new FieldDTO();
        field.setFieldCode(fieldCode);
        field.setFieldName(fieldName);
        field.setFieldSize(fieldSize);
        field.setFieldImage1(AppUtil.toBase64Pic(fieldImage1));
        field.setFieldImage2(AppUtil.toBase64Pic(fieldImage2));

        field.setFieldLocation(new Point(field.getFieldLocation().getX(), field.getFieldLocation().getY()));
        logger.info("Request received to save a new field: {}", field);
        try {
            fieldService.saveField(field,staffList);
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
            @RequestPart("updateStaffList") List<String> updateStaffList,
            @RequestPart("updateImage1") MultipartFile updateImage1,
            @RequestPart("updateImage2") MultipartFile updateImage2

    ) {
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setFieldCode(fieldCode);
        fieldDTO.setFieldName(updateFieldName);
        fieldDTO.setFieldLocation(new Point(updateLocation.getX(), updateLocation.getY()));
        fieldDTO.setFieldSize(updateSize);
        fieldDTO.setFieldImage1(AppUtil.toBase64Pic(updateImage1));
        fieldDTO.setFieldImage2(AppUtil.toBase64Pic(updateImage2));
        logger.info("Request received to update field with staff IDs {}: {}", updateStaffList, fieldDTO);
        try {
            fieldService.updateField(updateStaffList,fieldDTO);
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
