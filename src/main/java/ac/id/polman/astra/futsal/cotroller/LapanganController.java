package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.service.LapanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class LapanganController {
    @Autowired
    LapanganService lapanganService;

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

    @GetMapping("/Lapangan-add")
    public String gotoAdd(
            @RequestParam("id_merchant") int id_merchant,
            Model model
    ){
        model.addAttribute("MsLapangan", new MsLapangan());
        model.addAttribute("id_merchant", id_merchant);
        return "/lapangan/add";
    }

    @GetMapping("/Lapangan-edit")
    public String gotoEdit(
            @RequestParam("id_lapangan") int id_lapangan,
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
        return "redirect:/Lapangan?id_merchant="+msLapangan.getId_merchant();
    }

    @PostMapping("/editLapangan")
    public String editLapangan(MsLapangan msLapangan){
        MsLapangan old = lapanganService.getLapanganByIdLapangan(msLapangan.getIdLapangan());

        old.setModiby("Yang sekarang login");
        old.setModidate(LocalDateTime.now());

        old.setNama(msLapangan.getNama());
        old.setTipe_lapangan(msLapangan.getTipe_lapangan());
        old.setHarga(msLapangan.getHarga());
        old.setHarga_penerangan(msLapangan.getHarga_penerangan());
        old.setPanjang_lapangan(msLapangan.getPanjang_lapangan());
        old.setLebar_lapangan(msLapangan.getLebar_lapangan());

        lapanganService.saveLapangan(old);

        return "redirect:/Lapangan?id_merchant="+old.getId_merchant();
    }

    @GetMapping("/deleteLapangan")
    public String deleteLapangan(
            @RequestParam("id_lapangan") int id_lapangan
    ){
        MsLapangan old = lapanganService.getLapanganByIdLapangan(id_lapangan);

        old.setModiby("Yang sekarang login");
        old.setModidate(LocalDateTime.now());

        old.setStatus(0);

        lapanganService.saveLapangan(old);

        return "redirect:/Lapangan?id_merchant="+old.getId_merchant();
    }
    @GetMapping("/undoLapangan")
    public String undoLapangan(
            @RequestParam("id_lapangan") int id_lapangan
    ){
        MsLapangan old = lapanganService.getLapanganByIdLapangan(id_lapangan);

        old.setModiby("Yang sekarang login");
        old.setModidate(LocalDateTime.now());

        old.setStatus(1);

        lapanganService.saveLapangan(old);

        return "redirect:/Lapangan?id_merchant="+old.getId_merchant();
    }
}
