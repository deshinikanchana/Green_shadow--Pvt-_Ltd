package lk.ijse.aad68.crop_management_system.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("crop_management/healthCheck")
public class HealthCheckController {
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE','ROLE_MANAGER','ROLE_SCIENTIST')")
    @GetMapping
    public String healthCheck(){
        return "Crop Management System app in Green Shadow (Pvt) Ltd is running successfully";
    }
}
