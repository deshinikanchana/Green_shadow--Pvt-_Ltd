package lk.ijse.aad68.crop_management_system.Controller;

import lk.ijse.aad68.crop_management_system.CustomOBJ.StaffMemberResponse;
import lk.ijse.aad68.crop_management_system.DTO.IMPL.StaffDTO;
import lk.ijse.aad68.crop_management_system.Exception.DataPersistFailedException;
import lk.ijse.aad68.crop_management_system.Exception.StaffMemberNotFoundException;
import lk.ijse.aad68.crop_management_system.Service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("crop_management/staff")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StaffController {
    @Autowired
    private final StaffService staffService;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveMember(@RequestBody StaffDTO member) {
        if (member == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                staffService.saveMember(member);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") String staffId) {
        try {
            staffService.deleteMember(staffId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (StaffMemberNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffMemberResponse getSelectedMember(@PathVariable ("id") String staffId){
        return staffService.getSelectedMember(staffId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO> getAllMembers(){
        return staffService.getAllMembers();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER')")
    @PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateMember(
            @PathVariable ("id") String id,@RequestBody StaffDTO member){
        try {
            member.setStaffId(id);
            staffService.updateMember(member);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (StaffMemberNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
