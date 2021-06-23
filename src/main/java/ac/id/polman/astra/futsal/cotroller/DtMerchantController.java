package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.DtMerchant;
import ac.id.polman.astra.futsal.model.MsFasilitas;
import ac.id.polman.astra.futsal.service.DtMerchantService;
import ac.id.polman.astra.futsal.service.FasilitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DtMerchantController {
    @Autowired
    DtMerchantService dtMerchantService;

    @Autowired
    FasilitasService msFasilitasService;

    @GetMapping("/DtMerchant")
    public String getDtMerchant(
            @RequestParam("id_merchant") Integer id_merchant,
            Model model){
        List<DtMerchant> data = dtMerchantService.getAllDtMerchant();
        List<DtMerchant> dtMerchantList = new ArrayList<>();
        for ( DtMerchant dtMerchant : data )
        {
            if(dtMerchant.getId_merchant() == id_merchant){
                dtMerchantList.add(dtMerchant);
            }
        }
        List<MsFasilitas> msFasilitasList = msFasilitasService.getAllFacilities();

        model.addAttribute("listDtMerchant", dtMerchantList);
        model.addAttribute("id_merchant",id_merchant);
        model.addAttribute("listFasilitas",msFasilitasList);
        return "dtmerchant/list";
    }

    @GetMapping("DtMerchant-add")
    public String gotoAdd(
            @RequestParam("id_merchant") Integer id_merchant,
            Model model){

        List<MsFasilitas> msFasilitasList = msFasilitasService.getAllFacilities();

        model.addAttribute("dtMerchant", new DtMerchant());
        model.addAttribute("id_merchant", id_merchant);
        model.addAttribute("listFasilitas",msFasilitasList);
        return "dtmerchant/add";
    }

    @GetMapping("/DtMerchant-edit")
    public String gotoEdit(
            @RequestParam("id_dtmerchant") int id_dtmerchant,
            Model model
    ){
        DtMerchant dtMerchant = dtMerchantService.getDtMerchantByIdDtMerchant(id_dtmerchant);
        model.addAttribute("dtMerchant",dtMerchant);
        return "dtmerchant/edit";
    }

    // ===============================================

    @PostMapping("/addDtMerchant")
    public String addDtMerchant(
            @RequestParam("id_merchant") Integer id_merchant,
            DtMerchant dtMerchant){

        dtMerchant.setId_merchant(id_merchant);
        dtMerchant.setCreaby("Teddy yang login");
        dtMerchant.setCreadate(LocalDateTime.now());
        dtMerchant.setModiby("");
        dtMerchant.setModidate(LocalDateTime.now());
        dtMerchant.setStatus(1);
        dtMerchantService.saveDtMerchant(dtMerchant);
        return "redirect:/DtMerchant?id_merchant="+id_merchant;
    }

    @PostMapping("/editDtMerchant")
    public String editDtMerchant(DtMerchant dtMerchant){
        DtMerchant old = dtMerchantService.getDtMerchantByIdDtMerchant(dtMerchant.getId_dtmerchant());

        old.setJumlah(dtMerchant.getJumlah());
        old.setModiby("Yang sekarang login");
        old.setModidate(LocalDateTime.now());
        dtMerchantService.saveDtMerchant(old);

        return "redirect:/DtMerchant?id_merchant="+old.getId_merchant();
    }

    @GetMapping("/deleteDtMerchant")
    public String deleteDtMerchant(
            @RequestParam("id_dtmerchant") int id_dtmerchant
    ){
        DtMerchant old = dtMerchantService.getDtMerchantByIdDtMerchant(id_dtmerchant);

        old.setJumlah(0);
        old.setModiby("Yang sekarang login");
        old.setModidate(LocalDateTime.now());
        dtMerchantService.saveDtMerchant(old);
        return "redirect:/DtMerchant?id_merchant="+old.getId_merchant();
    }
}
