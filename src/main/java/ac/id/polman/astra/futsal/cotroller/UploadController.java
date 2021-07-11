package ac.id.polman.astra.futsal.cotroller;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Controller
public class UploadController {
    private final String UPLOAD_DIR_FOTO_MERCHANT            = "src/main/resources/static/images/merchant/foto/";
    private final String UPLOAD_DIR_BANNER          = "src/main/resources/static/images/merchant/banner/";
    private final String UPLOAD_DIR_FOTO_LAPANGAN   = "src/main/resources/static/images/lapangan/";
    private final String UPLOAD_DIR_LOGO_TIM        = "src/main/resources/static/images/lapangan/";
    private final String UPLOAD_DIR_BUKTI_TF        = "src/main/resources/static/images/bukti_transfer/";
    private final String UPLOAD_DIR_FOTO_PROFILE    = "src/main/resources/static/images/user/";


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public String upload_bukti_tf(MultipartFile file) {

        // check if file is empty
        if (file.isEmpty()) {
            System.out.println("Please select a file to upload.");
        }


        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //String fileName = formatter.format(LocalDateTime.now());

        // save the file on the local file system
        try {
            String extension = fileName.split("\\.")[1];
            fileName = "f"+formatter.format(LocalDateTime.now()) + "." + extension;
            Path path = Paths.get(UPLOAD_DIR_BUKTI_TF + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return success response
        System.out.println("You successfully uploaded " + fileName + '!');
        return fileName;
    }

    public String uploadFotoMerchant(MultipartFile file, String old) {

        // check if file is empty
        if (file.isEmpty()) {
            System.out.println("Please select a file to upload.");
        }

        // delete old
        if(!old.equals("none")){
            try{
                Path path = Paths.get(UPLOAD_DIR_FOTO_MERCHANT + old);
                Files.delete(path);
            }catch (Exception e){

            }
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //String fileName = formatter.format(LocalDateTime.now());

        // save the file on the local file system
        try {
            String extension = fileName.split("\\.")[1];
            fileName = "f"+formatter.format(LocalDateTime.now()) + "." + extension;
            Path path = Paths.get(UPLOAD_DIR_FOTO_MERCHANT + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return success response
        System.out.println("You successfully uploaded " + fileName + '!');
        return fileName;
    }

    public String uploadFotoLapangan(MultipartFile file) {

        // check if file is empty
        if (file.isEmpty()) {
            System.out.println("Please select a file to upload.");
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //String fileName = formatter.format(LocalDateTime.now());

        // save the file on the local file system
        try {
            String extension = fileName.split("\\.")[1];
            fileName = "l"+formatter.format(LocalDateTime.now()) + "." + extension;
            Path path = Paths.get(UPLOAD_DIR_FOTO_LAPANGAN + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf(e.getMessage());
        }

        // return success response
        System.out.println("You successfully uploaded " + fileName + '!');
        return fileName;
    }

    public void hapusFotoLapangan(String filename){
        try{
            Path path = Paths.get(UPLOAD_DIR_FOTO_MERCHANT + filename);
            Files.delete(path);
        }catch (Exception ex){

        }

    }

    public String uploadBannerMerchant(MultipartFile file, String old) {

        // check if file is empty
        if (file.isEmpty()) {
            System.out.println("Please select a file to upload.");
        }

        //delete old
        // delete old
        if(!old.equals("none")){
            try{
                Path path = Paths.get(UPLOAD_DIR_FOTO_MERCHANT + old);
                Files.delete(path);
            }catch (Exception e){

            }
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //String fileName = formatter.format(LocalDateTime.now());

        // save the file on the local file system
        try {
            String extension = fileName.split("\\.")[1];
            fileName = "b"+formatter.format(LocalDateTime.now()) + "." + extension;
            Path path = Paths.get(UPLOAD_DIR_BANNER + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return success response
        System.out.println("You successfully uploaded " + fileName + '!');
        return fileName;
    }

    public void uploadLogoTim(MultipartFile file, String old){

    }

    public String uploadFotoProfile(MultipartFile file, String old) {

        // check if file is empty
        if (file.isEmpty()) {
            System.out.println("Please select a file to upload.");
        }

        // delete old
        if(!old.equals("none")){
            try{
                Path path = Paths.get(UPLOAD_DIR_FOTO_PROFILE + old);
                Files.delete(path);
            }catch (Exception e){

            }
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //String fileName = formatter.format(LocalDateTime.now());

        // save the file on the local file system
        try {
            String extension = fileName.split("\\.")[1];
            fileName = "f"+formatter.format(LocalDateTime.now()) + "." + extension;
            Path path = Paths.get(UPLOAD_DIR_FOTO_PROFILE + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return success response
        System.out.println("You successfully uploaded " + fileName + '!');
        return fileName;
    }
}
