package lk.ijse.aad68.crop_management_system.Controller;

import lk.ijse.aad68.crop_management_system.CustomOBJ.EquipmentErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.EquipmentResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.FieldErrorResponse;
import lk.ijse.aad68.crop_management_system.CustomOBJ.FieldResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.EquipmentDTO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.FieldDTO;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.StaffDTO;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.EquipmentNotFoundException;
import lk.ijse.aad68.crop_management_system.Exception.FieldNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.EquipmentService;
import lk.ijse.aad68.crop_management_system.Util.AppUtil;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("crop_management/equipments")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EquipmentController {
    @Autowired
    private final EquipmentService equipmentService;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipment) {
        if (equipment == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                String staffId = equipment.getAssignedStaff();
                String fieldCode = equipment.getAssignedField();
                equipmentService.saveEquipment(equipment,staffId,fieldCode);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping(value = "allEquipments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAllEquipments(){
        return equipmentService.getAllEquipments();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentResponse getSelectedEquipment(@PathVariable ("equipmentId") String equipmentId)  {
        if(equipmentId.isEmpty() || equipmentId == null){
            return new EquipmentErrorResponse(1,"Not valid Equipment Id");
        }
        return equipmentService.getSelectedEquipment(equipmentId);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER')")
    @PatchMapping(value = "/{equipmentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(
            @PathVariable ("equipmentId") String id,
            @RequestPart("updateName") String updateName,
            @RequestPart ("updateStatus") String updateStatus,
            @RequestPart ("updateType") String updateType,
            @RequestPart ("updateStaff") String updateStaff,
            @RequestPart ("updateField") String updateField

    ){
        try {
            var updatedEquipment = new EquipmentDTO();
            updatedEquipment.setEquipmentName(updateName);
            updatedEquipment.setType(updateType);
            updatedEquipment.setStatus(updateStatus);

            equipmentService.updateEquipment(updatedEquipment,updateStaff,updateField);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (FieldNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER')")
    @DeleteMapping(value ="/{equipmentId}" )
    public ResponseEntity<Void> deleteEquipment(@PathVariable ("equipmentId") String equipmentId) {
        try {
            equipmentService.deleteEquipment(equipmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EquipmentNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
