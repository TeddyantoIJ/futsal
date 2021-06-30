package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsBiaya;
import ac.id.polman.astra.futsal.service.BiayaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BiayaController {
    @Autowired
    BiayaService biayaService;

    @GetMapping("/Biaya")
    public String getBiaya(
            Model model
    ){
        List<MsBiaya> msBiayaList = biayaService.getAllBiaya();
        model.addAttribute("listBiaya", msBiayaList);
        return "biaya/list";
    }

    @GetMapping("/Biaya-add")
    public String gotoAdd(
            Model model
    ){
        model.addAttribute("msBiaya", new MsBiaya());
        return "biaya/add";
    }

    @GetMapping("/Biaya-edit")
    public String gotoEdit(
            @RequestParam("id_biaya") int id_biaya,
            Model model
    ){
        MsBiaya msBiaya = biayaService.getBiaya(id_biaya);
        model.addAttribute("msBiaya", msBiaya);
        return "biaya/edit";
    }

    //============================
    @PostMapping("/addBiaya")
    public String addBiaya(
            @RequestParam("nominal") int nominal,
            @RequestParam("keterangan") String keterangan
    ){
        MsBiaya msBiaya = new MsBiaya();
        msBiaya.setNominal(nominal);
        msBiaya.setKeterangan(keterangan);
        msBiaya.setCreaby("ivan");
        msBiaya.setCreadate(LocalDateTime.now());
        msBiaya.setModiby("");
        msBiaya.setModidate(LocalDateTime.now());
        msBiaya.setStatus(1);

        biayaService.saveBiaya(msBiaya);
        return "redirect:/Biaya";
    }
    @PostMapping("/editBiaya")
    public String editStatus(
            @RequestParam("id_biaya") int id_biaya,
            @RequestParam("nominal") int nominal,
            @RequestParam("keterangan") String keterangan
    ){
        MsBiaya msBiaya = biayaService.getBiaya(id_biaya);

        msBiaya.setNominal(nominal);
        msBiaya.setKeterangan(keterangan);
        msBiaya.setModiby("Pengedit");
        msBiaya.setModidate(LocalDateTime.now());

        biayaService.saveBiaya(msBiaya);
        return "redirect:/Biaya";
    }

    @GetMapping("/deleteBiaya")
    public String deleteBiaya(
            @RequestParam("id_biaya") int id_biaya
    ){
        MsBiaya msBiaya = biayaService.getBiaya(id_biaya);

        msBiaya.setModiby("yang login saat ini");
        msBiaya.setModidate(LocalDateTime.now());
        if(msBiaya.getStatus() == 1){
            msBiaya.setStatus(0);
        }else{
            msBiaya.setStatus(1);
        }
        biayaService.saveBiaya(msBiaya);
        return "redirect:/Biaya";
    }
}
