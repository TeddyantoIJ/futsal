package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.model.MsUser;
import ac.id.polman.astra.futsal.service.LapanganService;
import ac.id.polman.astra.futsal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class LapanganController {
    @Autowired
    LapanganService lapanganService;
    @Autowired
    UserService userService;

    @GetMapping("/Lapangan")
    public String getLapangan(
            @RequestParam("id_merchant") int id_merchant,
            Model model
    ){
        List<MsLapangan> msLapanganList = lapanganService.getAllLapanganByIdMerchant(id_merchant);
        model.addAttribute("listLapangan", msLapanganList);
        model.addAttribute("id_merchant", id_merchant);
        return "/lapangan/list";
    }

    @GetMapping("/LapanganAdmin")
    public String getLapanganAdmin(
            @RequestParam("id_merchant") int id_merchant,
            Model model
    ){
        List<MsLapangan> msLapanganList = lapanganService.getAllLapanganByIdMerchant(id_merchant);
        model.addAttribute("listLapangan", msLapanganList);
        model.addAttribute("id_merchant", id_merchant);
        return "/merchant/listlapangan";
    }

    @PostMapping("/add-court")
    public String gotoAdd(
            @RequestParam("id_merchant") int id_merchant,
            Model model
    ){
        model.addAttribute("MsLapangan", new MsLapangan());
        model.addAttribute("id_merchant", id_merchant);
        return "/lapangan/add";
    }

    @GetMapping("/edit-court/{id_lapangan}")
    public String gotoEdit(
            @PathVariable int id_lapangan,
            Model model
    ){
        MsLapangan msLapangan = lapanganService.getLapanganByIdLapangan(id_lapangan);
        model.addAttribute("MsLapangan", msLapangan);
        return "/lapangan/edit";
    }

    @GetMapping("/Lapangan-detail")
    public String gotoDetail(
            @RequestParam("id_lapangan") int id_lapangan,
            Model model
    ){
        MsLapangan msLapangan = lapanganService.getLapanganByIdLapangan(id_lapangan);
        model.addAttribute("MsLapangan", msLapangan);
        return "/lapangan/detail";
    }

    // ===================================
    @PostMapping("/addLapangan")
    public String addLapangan(MsLapangan msLapangan){

        msLapangan.setCreaby("Teddy yang login");
        msLapangan.setCreadate(LocalDateTime.now());
        msLapangan.setModiby("");
        msLapangan.setModidate(LocalDateTime.now());
        msLapangan.setStatus(1);

        lapanganService.saveLapangan(msLapangan);
        return "redirect:/my-merchant";
    }

    @PostMapping("/editLapangan")
    public String editLapangan(MsLapangan msLapangan, HttpSession session){
        MsLapangan old = lapanganService.getLapanganByIdLapangan(msLapangan.getIdLapangan());
        MsUser a = new MsUser();
        try {
            a = userService.getUserById((int) session.getAttribute("id_user"));
        }catch (Exception e){
            return "redirect:/page-login";
        }


        old.setModiby(a.getEmail());
        old.setModidate(LocalDateTime.now());

        old.setNama(msLapangan.getNama());
        old.setTipe_lapangan(msLapangan.getTipe_lapangan());
        old.setHarga(msLapangan.getHarga());
        old.setHarga_penerangan(msLapangan.getHarga_penerangan());
        old.setPanjang_lapangan(msLapangan.getPanjang_lapangan());
        old.setLebar_lapangan(msLapangan.getLebar_lapangan());

        lapanganService.saveLapangan(old);

        return "redirect:/my-merchant";
    }

    @GetMapping("/delete-court/{id_lapangan}")
    public String deleteLapangan(
            @PathVariable int id_lapangan,
            HttpSession session
    ){
        MsLapangan old = lapanganService.getLapanganByIdLapangan(id_lapangan);
        MsUser a = new MsUser();
        try {
            a = userService.getUserById((int) session.getAttribute("id_user"));
        }catch (Exception e){
            return "redirect:/page-login";
        }
        old.setModiby(a.getEmail());
        old.setModidate(LocalDateTime.now());

        old.setStatus(0);

        lapanganService.saveLapangan(old);

        return "redirect:/my-merchant";
    }
    @GetMapping("/undo-court/{id_lapangan}")
    public String undoLapangan(
            @PathVariable int id_lapangan,
            HttpSession session
    ){
        MsLapangan old = lapanganService.getLapanganByIdLapangan(id_lapangan);
        MsUser a = new MsUser();
        try {
            a = userService.getUserById((int) session.getAttribute("id_user"));
        }catch (Exception e){
            return "redirect:/page-login";
        }

        old.setModiby(a.getEmail());
        old.setModidate(LocalDateTime.now());

        old.setStatus(1);

        lapanganService.saveLapangan(old);

        return "redirect:/my-merchant";
    }
}
