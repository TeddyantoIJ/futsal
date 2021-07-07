package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsFasilitas;
import ac.id.polman.astra.futsal.service.FasilitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class FasilitasController {
    @Autowired
    FasilitasService fasilitasService;

    @GetMapping("/Fasilitas")
    public String getFasilitas(Model model)
    {
        List<MsFasilitas> msFasilitasList = fasilitasService.findAllByStatus(1);
        model.addAttribute("listFasilitas", msFasilitasList);
        return "fasilitas/list";
    }

    @GetMapping("/Fasilitas-add")
    public String gotoAdd(Model model){
        model.addAttribute("msFasilitas", new MsFasilitas());
        return "fasilitas/add";
    }

    @GetMapping("/Fasilitas-edit")
    public String gotoEdit(
            @RequestParam("id_fasilitas") int id_fasilitas,
            Model model
    ){
        MsFasilitas msFasilitas = fasilitasService.getFacilities(id_fasilitas);
        model.addAttribute("msFasilitas", msFasilitas);
        return "fasilitas/edit";
    }

    //========================================
    @PostMapping("/addFasilitas")
    public String addFasilitas(MsFasilitas msFasilitas){
        msFasilitas.setCreaby("Ivan(harusnya ambil nama yang bikin)");
        msFasilitas.setCreadate(LocalDateTime.now());
        msFasilitas.setModiby("");
        msFasilitas.setModidate(LocalDateTime.now());
        msFasilitas.setStatus(1);

        fasilitasService.savefasilitas(msFasilitas);
        return "redirect:/Fasilitas";
    }

    @PostMapping("/editFasilitas")
    public String editFasilitas(
            @RequestParam("idFasilitas") int idFasilitas,
            @RequestParam("fasilitas") String fasilitas,
            @RequestParam("icon") String icon
    ){
        MsFasilitas msFasilitas = fasilitasService.getFacilities(idFasilitas);

        msFasilitas.setFasilitas(fasilitas);
        msFasilitas.setIcon(icon);
        msFasilitas.setModiby("yang login saat ini");
        msFasilitas.setModidate(LocalDateTime.now());

        fasilitasService.savefasilitas(msFasilitas);
        return "redirect:/Fasilitas";
    }

    @GetMapping("/deleteFasilitas")
    public String deleteFasilitas(
            @RequestParam("id_fasilitas") int idFasilitas
    ){
        MsFasilitas msFasilitas = fasilitasService.getFacilities(idFasilitas);

        msFasilitas.setModiby("yang login saat ini");
        msFasilitas.setModidate(LocalDateTime.now());
        if(msFasilitas.getStatus() == 1){
            msFasilitas.setStatus(0);
        }else{
            msFasilitas.setStatus(1);
        }
        fasilitasService.savefasilitas(msFasilitas);
        return "redirect:/Fasilitas";
    }
}
