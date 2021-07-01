package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsTim;
import ac.id.polman.astra.futsal.service.TimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TimController {
    @Autowired
    TimService timService;

    @GetMapping("/Tim")
    public String getTim(Model model){
        List<MsTim> data = timService.getAllTim();
        List<MsTim> msTimList = new ArrayList<>();
        for ( MsTim msTim : data )
        {
            if(msTim.getStatus() == 1){
                msTimList.add(msTim);
            }
        }

        model.addAttribute("listTim", data);
        return "tim/list";
    }

    @GetMapping("/Tim-add")
    public String gotoAdd(Model model){
        model.addAttribute("timObj", new MsTim());
        return "tim/add";
    }

    @GetMapping("/Tim-edit")
    public String gotoEdit(Model model, @RequestParam(name = "id_tim") int id_tim){
        MsTim msTim = new MsTim();
        msTim = timService.getTimById(id_tim);
        model.addAttribute("timObj", msTim);
        return "tim/edit";
    }

    @GetMapping("/Tim-detail")
    public String gotDetail(Model model, @RequestParam(name = "id_tim") int id_tim){
        MsTim msTim = new MsTim();
        msTim = timService.getTimById(id_tim);
        model.addAttribute("timObj", msTim);
        return "tim/detail";
    }

    // ================================================================
    @PostMapping("/addTim")
    public String addTim(
            MsTim msTim,
            @RequestParam("file") MultipartFile file,
            @RequestParam("file1") MultipartFile file1){
        UploadController uploadController = new UploadController();

        String logo = uploadController.uploadFotoMerchant(file, "none");
        String banner = uploadController.uploadBannerMerchant(file1, "none");

        msTim.setIdUser(1);
//        msTim.setTglBerdiri(LocalDateTime.now());
        msTim.setLogo(logo);
        msTim.setBanner(banner);
        msTim.setCreaby("Teddy(harusnya ambil nama yang bikin)");
        msTim.setCreadate(LocalDateTime.now());
        msTim.setModiby("");
        msTim.setModidate(LocalDateTime.now());
        msTim.setStatus(1);

        timService.saveTim(msTim);
        return "redirect:/page-login";
    }

    @PostMapping("/editTim")
    public String editTim(
            MsTim msTim,
            @RequestParam("file") MultipartFile file,
            @RequestParam("file1") MultipartFile file1
    ){
        UploadController uploadController = new UploadController();
        MsTim oldTim = timService.getTimById(msTim.getIdTim());

        msTim.setIdUser(oldTim.getIdUser());
        msTim.setCreaby(oldTim.getCreaby());
        msTim.setCreadate(oldTim.getCreadate());
        msTim.setModiby("yang login sekarang");
        msTim.setModidate(LocalDateTime.now());
        msTim.setStatus(oldTim.getStatus());

        String logo;
        String banner;

        if(!file.isEmpty()){
            logo = uploadController.uploadFotoMerchant(file, oldTim.getLogo());
            msTim.setLogo(logo);
        }else{
            msTim.setLogo(oldTim.getLogo());
        }

        if(!file1.isEmpty()){
            banner = uploadController.uploadBannerMerchant(file1, oldTim.getBanner());
            msTim.setBanner(banner);
        }else{
            msTim.setBanner(oldTim.getBanner());
        }

        timService.update(msTim);
        return "redirect:/Tim";
    }

    @GetMapping("/deleteTim")
    public String deleteTim(
            @RequestParam("id_tim") Integer id_tim
    ){
        MsTim msTim = timService.getTimById(id_tim);
        msTim.setStatus(0);
        timService.update(msTim);
        return "redirect:/Tim";
    }
}
