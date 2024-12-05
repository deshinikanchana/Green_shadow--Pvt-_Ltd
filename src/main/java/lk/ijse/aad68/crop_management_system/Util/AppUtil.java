package lk.ijse.aad68.crop_management_system.Util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public class AppUtil {
    public static String toBase64Pic(MultipartFile picture){
        String picBase64 = null;
        try {
            byte [] proPicBytes = picture.getBytes();
            picBase64 =  Base64.getEncoder().encodeToString(proPicBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return picBase64;
    }

    public static String createStaffId(String currentMaxId){
        int newId;

        if (currentMaxId == null) {
            newId = 1;
        } else {
            String numericPart = currentMaxId.replace("Staff-", "");
            newId = Integer.parseInt(numericPart) + 1;
        }
        return String.format("Staff-%04d", newId);
    }

    public static String createFieldCode(String currentMaxId){
        int newId;

        if (currentMaxId == null) {
            newId = 1;
        } else {
            String numericPart = currentMaxId.replace("Field-", "");
            newId = Integer.parseInt(numericPart) + 1;
        }
        return String.format("Field-%04d", newId);
    }

    public static String createCropCode(String currentMaxId){
        int newId;

        if (currentMaxId == null) {
            newId = 1;
        } else {
            String numericPart = currentMaxId.replace("Crop-", "");
            newId = Integer.parseInt(numericPart) + 1;
        }
        return String.format("Crop-%04d", newId);
    }

    public static String createVehicleCode(String currentMaxId){
        int newId;

        if (currentMaxId == null) {
            newId = 1;
        } else {
            String numericPart = currentMaxId.replace("Vehicle-", "");
            newId = Integer.parseInt(numericPart) + 1;
        }
        return String.format("Vehicle-%04d", newId);
    }

    public static String createEquipmentId(String currentMaxId){
        int newId;

        if (currentMaxId == null) {
            newId = 1;
        } else {
            String numericPart = currentMaxId.replace("Eq-", "");
            newId = Integer.parseInt(numericPart) + 1;
        }
        return String.format("Eq-%04d", newId);
    }

    public static String createLogCode(String currentMaxId){
        int newId;

        if (currentMaxId == null) {
            newId = 1;
        } else {
            String numericPart = currentMaxId.replace("Log-", "");
            newId = Integer.parseInt(numericPart) + 1;
        }
        return String.format("Log-%04d", newId);
    }
}
