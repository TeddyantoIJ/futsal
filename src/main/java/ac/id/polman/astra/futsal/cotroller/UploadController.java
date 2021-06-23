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
    private final String UPLOAD_DIR_FOTO            = "D:/Semester4/PRG7/Project/futsal/src/main/resources/static/images/merchant/foto/";
    private final String UPLOAD_DIR_BANNER          = "D:/Semester4/PRG7/Project/futsal/src/main/resources/static/images/merchant/banner/";
    private final String UPLOAD_DIR_FOTO_LAPANGAN   = "D:/Semester4/PRG7/Project/futsal/src/main/resources/static/images/lapangan/";


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

//    @GetMapping("/")
//    public String homepage() {
//        return "merchant/merchant";
//    }

    //@PostMapping("/upload")
    public String uploadFotoMerchant(MultipartFile file, String old) {

        // check if file is empty
        if (file.isEmpty()) {
            System.out.println("Please select a file to upload.");
        }

        // delete old
        if(!old.equals("none")){
            try{
                Path path = Paths.get(UPLOAD_DIR_FOTO + old);
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
            Path path = Paths.get(UPLOAD_DIR_FOTO + fileName);
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
            Path path = Paths.get(UPLOAD_DIR_FOTO + filename);
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
                Path path = Paths.get(UPLOAD_DIR_FOTO + old);
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

}
