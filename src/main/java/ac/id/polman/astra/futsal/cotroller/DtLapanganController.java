package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.DtFotolapangan;
import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.model.MsUser;
import ac.id.polman.astra.futsal.service.DtFotoLapanganService;
import ac.id.polman.astra.futsal.service.LapanganService;
import ac.id.polman.astra.futsal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DtLapanganController {
    @Autowired
    DtFotoLapanganService dtFotoLapanganService;
    @Autowired
    UserService userService;

    @GetMapping("/images-court/{id_lapangan}")
    public String getFotoLapangan(
            @PathVariable int id_lapangan,
            Model model
    ){

        List<DtFotolapangan> dtFotolapanganList = dtFotoLapanganService.getAllDtFotoLapanganByIdLapangan(id_lapangan);
        model.addAttribute("listDtFotoLapangan", dtFotolapanganList);
        model.addAttribute("id_lapangan",id_lapangan);
        return "/dtfotolapangan/list";
    }

    @GetMapping("/Foto-Lapangan-add")
    public String gotoAdd(
            @RequestParam("id_lapangan") int id_lapangan,
            Model model
    ){
        model.addAttribute("id_lapangan",id_lapangan);
        return "/dtfotolapangan/add";
    }

    @GetMapping("/edit-images-court/{id_foto}")
    public String gotoEdit(
            @PathVariable int id_foto,
            Model model
    ){
        DtFotolapangan dtFotolapangan = dtFotoLapanganService.getDtFotoLapanganById(id_foto);
        model.addAttribute("dtFotoLapangan", dtFotolapangan);
        return "/dtfotolapangan/edit";
    }

    // ===================================================
    @PostMapping("/addFotoLapangan")
    public String addFotoLapangan(
            @RequestParam("id_lapangan") int id_lapangan,
            @RequestParam("caption") String caption,
            @RequestParam("file") MultipartFile file,
            HttpSession session
    ){
        UploadController uploadController = new UploadController();
        MsUser a = new MsUser();
        try {
            a = userService.getUserById((int) session.getAttribute("id_user"));
        }catch (Exception e){
            return "redirect:/page-login";
        }
        String foto = uploadController.uploadFotoLapangan(file);

        DtFotolapangan dtFotolapangan = new DtFotolapangan();

        dtFotolapangan.setFoto(foto);
        dtFotolapangan.setIdLapangan(id_lapangan);

        if(!caption.isEmpty()){
            dtFotolapangan.setCaption(caption);
        }

        dtFotolapangan.setCreaby(a.getEmail());
        dtFotolapangan.setCreadate(LocalDateTime.now());
        dtFotolapangan.setModiby("");
        dtFotolapangan.setModidate(LocalDateTime.now());
        dtFotolapangan.setStatus(1);

        dtFotoLapanganService.saveFotoLapangan(dtFotolapangan);

        return "redirect:/images-court/"+id_lapangan;
    }

    @PostMapping("/editFotoLapangan")
    public String editFotoLapangan(
            @RequestParam("id_foto") int id_foto,
            @RequestParam("caption") String caption
    ){
        DtFotolapangan dtFotolapangan = dtFotoLapanganService.getDtFotoLapanganById(id_foto);

        dtFotolapangan.setModiby("yang login sekarang");
        dtFotolapangan.setModidate(LocalDateTime.now());
        dtFotolapangan.setCaption(caption);

        dtFotoLapanganService.saveFotoLapangan(dtFotolapangan);
        return "redirect:/images-court/"+dtFotolapangan.getIdLapangan();
    }

    @GetMapping("/deletePotoLapangan")
    public String deletePotoLapangan(
            @RequestParam("id_foto") int id_foto
    ){
        DtFotolapangan dtFotolapangan = dtFotoLapanganService.getDtFotoLapanganById(id_foto);
        UploadController uploadController = new UploadController();

        uploadController.hapusFotoLapangan(dtFotolapangan.getFoto());

        dtFotoLapanganService.deleteFotoLapangan(dtFotolapangan);
        return "redirect:/Foto-Lapangan?id_lapangan="+dtFotolapangan.getIdLapangan();
    }
}
