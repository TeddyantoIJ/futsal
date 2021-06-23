package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsAkun;
import ac.id.polman.astra.futsal.service.AkunService;
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
public class AkunController {
    @Autowired
    AkunService akunService;

    @GetMapping("/Akun")
    public String getAkun(Model model){
        List<MsAkun> data = akunService.getAllAkun();
        List<MsAkun> msAkunList = new ArrayList<>();
        for ( MsAkun msAkun : data )
        {
            if(msAkun.getStatus() == 1){
                msAkunList.add(msAkun);
            }
        }
//        model.addAttribute("listMerchant", msMerchantList);
        model.addAttribute("listAkun", data);
        return "akun/list";
    }

    @GetMapping("/Akun-add")
    public String gotoAdd(
            Model model
    ){
        model.addAttribute("msAkun", new MsAkun());
        return "akun/add";
    }

    @GetMapping("/Akun-edit")
    public String gotoEdit(
            @RequestParam("id_akun") int id_akun,
            Model model
    ){
        MsAkun msAkun = akunService.getAkunByIdAkun(id_akun);
        model.addAttribute("msAkun", msAkun);
        return "akun/edit";
    }

    @GetMapping("/Akun-detail")
    public String gotoDetail(
            @RequestParam("id_akun") int id_akun,
            Model model
    ){
        MsAkun msAkun = akunService.getAkunByIdAkun(id_akun);
        model.addAttribute("MsAkun", msAkun);
        return "/akun/detail";
    }

    // ===================================
    @PostMapping("/addAkun")
    public String addAkun(MsAkun msAkun){

        msAkun.setIdRole(1);
        msAkun.setCreaby("Muhamad Ivan");
        msAkun.setCreadate(LocalDateTime.now());
        msAkun.setModiby("");
        msAkun.setModidate(LocalDateTime.now());
        msAkun.setStatus(1);

        akunService.saveAkun(msAkun);
        return "redirect:/Akun?id_role="+msAkun.getIdRole();
    }

    @PostMapping("/editAkun")
    public String editAkun(
            @RequestParam("id_akun") int id_akun,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){
        MsAkun msAkun = akunService.getAkunByIdAkun(id_akun);

        msAkun.setUsername(username);
        msAkun.setPassword(password);
        msAkun.setModiby("Yang login sekarang");
        msAkun.setModidate(LocalDateTime.now());

        akunService.saveAkun(msAkun);
        return "redirect:/Akun";
    }

    @GetMapping("/deleteAkun")
    public String deleteAkun(
            @RequestParam("id_akun") int id_akun
    ){
        MsAkun ma = akunService.getAkunByIdAkun(id_akun);

        ma.setModiby("Yang sekarang login");
        ma.setModidate(LocalDateTime.now());

        ma.setStatus(0);

        akunService.saveAkun(ma);

        return "redirect:/Akun?id_role="+ma.getIdRole();
    }
}
