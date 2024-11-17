package lk.ijse.aad68.crop_management_system.Controller;

import lk.ijse.aad68.crop_management_system.CustomOBJ.CropErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.CropResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.CropDTO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.FieldDTO;
import lk.ijse.aad68.crop_management_system.Exception.CropNotFoundException;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Service.CropService;
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
@RequestMapping("crop_management/crops")
public class CropController {

    @Autowired
    private final CropService cropService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(@RequestBody CropDTO crop) {
        if (crop == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                cropService.saveCrop(crop);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "allCrops", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrops(){
        return cropService.getAllCrops();
    }

    @GetMapping(value = "/{cropCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getSelectedCrop(@PathVariable ("cropCode") String cropCode)  {
        if(cropCode.isEmpty() || cropCode == null){
            return new CropErrorResponse(1,"Not valid Crop Code");
        }
        return cropService.getSelectedCrop(cropCode);
    }

    @PatchMapping(value = "/{cropCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCrop(
            @PathVariable ("cropCode") String id,
            @RequestPart("updateCommonName") String updateCommonName,
            @RequestPart ("updateScientificName") String updateScientificName,
            @RequestPart ("updateCategory") String updateCategory,
            @RequestPart ("updateCropSeason") String updateCropSeason,
            @RequestPart ("updateCropImage") MultipartFile updateCropImage,
            @RequestPart("updateField") FieldDTO updateField
    ){
        try {
            String updateBase64CropImg = AppUtil.toBase64Pic(updateCropImage);
            var updatedCrop = new CropDTO();
            updatedCrop.setCommonName(updateCommonName);
            updatedCrop.setScientificName(updateScientificName);
            updatedCrop.setCropImage(updateBase64CropImg);
            updatedCrop.setCategory(updateCategory);
            updatedCrop.setCropSeason(updateCropSeason);
            updatedCrop.setField(updateField);
            cropService.updateCrop(updatedCrop);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CropNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value ="/{cropCode}" )
    public ResponseEntity<Void> deleteCrop(@PathVariable ("cropCode") String cropCode) {
        try {
            cropService.deleteCrop(cropCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CropNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
