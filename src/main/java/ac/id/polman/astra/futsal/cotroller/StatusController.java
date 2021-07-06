package ac.id.polman.astra.futsal.cotroller;


import ac.id.polman.astra.futsal.model.MsStatus;
import ac.id.polman.astra.futsal.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class StatusController {
    @Autowired
    StatusService statusService;

    @GetMapping("/Status")
    public String getStatus(
            Model model
    ){
        List<MsStatus> msStatusList = statusService.findAllByStatus(1);
        model.addAttribute("listStatus", msStatusList);
        return "status/list";
    }

    @GetMapping("/Status-add")
    public String gotoAdd(
            Model model
    ){
        model.addAttribute("msStatus", new MsStatus());
        return "status/add";
    }

    @GetMapping("/Status-edit")
    public String gotoEdit(
            @RequestParam("id_status") int id_status,
            Model model
    ){
        MsStatus msStatus = statusService.getStatus(id_status);
        model.addAttribute("msStatus", msStatus);
        return "status/edit";
    }

    //============================
    @PostMapping("/addStatus")
    public String addStatus(
            @RequestParam("keterangan") String keterangan
    ){
        MsStatus msStatus = new MsStatus();
        msStatus.setKeterangan(keterangan);
        msStatus.setCreaby("Teddy(harusnya ambil nama yang bikin)");
        msStatus.setCreadate(LocalDateTime.now());
        msStatus.setModiby("");
        msStatus.setModidate(LocalDateTime.now());
        msStatus.setStatus(1);

        statusService.saveStatus(msStatus);
        return "redirect:/Status";
    }
    @PostMapping("/editStatus")
    public String editStatus(
            @RequestParam("id_status") int id_status,
            @RequestParam("keterangan") String keterangan
    ){
        MsStatus msStatus = statusService.getStatus(id_status);

        msStatus.setKeterangan(keterangan);
        msStatus.setModiby("Yang login sekarang");
        msStatus.setModidate(LocalDateTime.now());

        statusService.saveStatus(msStatus);
        return "redirect:/Status";
    }

    @GetMapping("/deleteStatus")
    public String deleteStatus(
            @RequestParam("id_status") int id_status
    ){
        MsStatus msStatus = statusService.getStatus(id_status);

        msStatus.setModiby("yang login saat ini");
        msStatus.setModidate(LocalDateTime.now());
        if(msStatus.getStatus() == 1){
            msStatus.setStatus(0);
        }else{
            msStatus.setStatus(1);
        }
        statusService.saveStatus(msStatus);
        return "redirect:/Status";
    }
}
