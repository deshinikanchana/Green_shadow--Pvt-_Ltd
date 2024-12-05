package lk.ijse.aad68.crop_management_system.Controller;

import lk.ijse.aad68.crop_management_system.CustomOBJ.CropErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.CropResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.CropDTO;
import lk.ijse.aad68.crop_management_system.Exception.CropNotFoundException;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Service.CropService;
import lk.ijse.aad68.crop_management_system.Util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("crop_management/crops")
@CrossOrigin("*")
public class CropController {

    @Autowired
    private final CropService cropService;

    private static final Logger logger = LoggerFactory.getLogger(CropController.class);
    @PreAuthorize("hasRole('ROLE_SCIENTIST') or hasRole('ROLE_MANAGER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(@RequestPart("cropCode") String cropCode,
                                         @RequestPart("commonName") String commonName,
                                         @RequestPart("scientificName")String scientificName,
                                         @RequestPart("cropImage") MultipartFile cropImage,
                                         @RequestPart("category") String category,
                                         @RequestPart("cropSeason") String cropSeason,
                                         @RequestPart("fieldCode") String fieldCode) {


        CropDTO saveCrop = new CropDTO();
        saveCrop.setCropCode(cropCode);
        saveCrop.setCommonName(commonName);
        saveCrop.setScientificName(scientificName);
        saveCrop.setCropImage(AppUtil.toBase64Pic(cropImage));
        saveCrop.setCropSeason(cropSeason);
        saveCrop.setCategory(category);

        if (saveCrop == null){
            logger.info("Null Request received for save Crop");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                logger.info("Request received to save a new crop: {}", saveCrop);
                cropService.saveCrop(saveCrop, fieldCode);
                logger.info("Crop saved successfully: {}", saveCrop);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (CropNotFoundException e){
                logger.error("Failed to save crop: {}", saveCrop, e);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (DataPersistFailedException e) {
                logger.error("Failed to save crop: {}", saveCrop, e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                logger.error("Internal server error while saving crop: {}", saveCrop, e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping(value = "allCrops", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrops(){
        logger.info("Fetching All Crops in Database");
        return cropService.getAllCrops();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping(value = "/{cropCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getSelectedCrop(@PathVariable ("cropCode") String cropCode)  {
        if(cropCode.isEmpty() || cropCode == null){
            logger.error("Invalid Crop Code Received");
            return new CropErrorResponse(1,"Not valid Crop Code");
        }
        logger.info("Fetching crop with ID: {}", cropCode);
        return cropService.getSelectedCrop(cropCode);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_SCIENTIST')")
    @PatchMapping(value = "/{cropCode}",produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(
            @PathVariable ("cropCode") String id,
            @RequestPart("updateCommonName") String updateCommonName,
            @RequestPart ("updateScientificName") String updateScientificName,
            @RequestPart ("updateCategory") String updateCategory,
            @RequestPart ("updateCropSeason") String updateCropSeason,
            @RequestPart ("updateCropImage") MultipartFile updateCropImage,
            @RequestPart("updateField") String updateFieldCode
    ){
        try {
            String updateBase64CropImg = AppUtil.toBase64Pic(updateCropImage);
            var updatedCrop = new CropDTO();
            updatedCrop.setCropCode(id);
            updatedCrop.setCommonName(updateCommonName);
            updatedCrop.setScientificName(updateScientificName);
            updatedCrop.setCropImage(updateBase64CropImg);
            updatedCrop.setCategory(updateCategory);
            updatedCrop.setCropSeason(updateCropSeason);


            logger.info("Request received to update crop: {}", updatedCrop);

            cropService.updateCrop(updatedCrop,updateFieldCode);
            logger.info("Crop updated successfully: {}", updatedCrop);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CropNotFoundException e){
            logger.error("Failed to update crop: " ,id ,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Internal server error while updating crop: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_SCIENTIST')")
    @DeleteMapping(value ="/{cropCode}" )
    public ResponseEntity<Void> deleteCrop(@PathVariable ("cropCode") String cropCode) {
        try {
            logger.info("Request received to delete crop with ID: {}", cropCode);
            cropService.deleteCrop(cropCode);
            logger.info("Crop deleted successfully: {}", cropCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CropNotFoundException e){
            logger.error("Failed to delete crop: {}", cropCode,e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Unexpected error occurred while deleting crop: {}", cropCode,e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
