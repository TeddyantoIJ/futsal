package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsAkun;
import ac.id.polman.astra.futsal.model.MsTim;
import ac.id.polman.astra.futsal.model.MsUser;
import ac.id.polman.astra.futsal.model.TrDaftarTim;
import ac.id.polman.astra.futsal.service.AkunService;
import ac.id.polman.astra.futsal.service.TimService;
import ac.id.polman.astra.futsal.service.Tr_daftar_tim_service;
import ac.id.polman.astra.futsal.service.UserService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TimController {
    @Autowired
    TimService timService;

    @Autowired
    UserService userService;

    @Autowired
    AkunService akunService;

    @Autowired
    Tr_daftar_tim_service trDaftarTimService;

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
    public String gotoAdd(Model model, HttpSession session){
        int idus = -1;
        try{
            idus = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }

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
            MsTim msTim, MsUser msUser, MsAkun msAkun,
            @RequestParam("file") MultipartFile file,
            @RequestParam("file1") MultipartFile file1, HttpSession session){
        int idus = -1;
        try{
            idus = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }

        UploadController uploadController = new UploadController();
        String logo = uploadController.uploadLogoTim(file, "none");
        String banner = uploadController.uploadBannerTim(file1, "none");

        msTim.setIdUser(idus);
        msTim.setLogo(logo);
        msTim.setBanner(banner);
        msTim.setCreaby("Teddy(harusnya ambil nama yang bikin)");
        msTim.setCreadate(LocalDateTime.now());
        msTim.setModiby("");
        msTim.setModidate(LocalDateTime.now());
        msTim.setStatus(1);
        timService.saveTim(msTim);

        //update user terdapat idtim
        MsUser a = userService.getUserById(idus);
        a.setIdUser(idus);
        a.setIdTim(msTim.getIdTim());
        userService.update(a);

        //update user punya akses manager tim
        MsAkun b = akunService.getAkunByIdAkun(a.getIdAkun());
        b.setIdAkun(a.getIdAkun());
        b.setIdRole(4);
        akunService.update(b);
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

    @PostMapping("/ask-join-tim-index")
    public String ask_join_tim_index(
            HttpSession session,
            TrDaftarTim daftar,
            Model model
    ){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        MsUser user = userService.getUserById(id);
        TrDaftarTim pendaftaran = new TrDaftarTim();
        pendaftaran.setIdTim(daftar.getIdTim());
        pendaftaran.setAlasan(daftar.getAlasan());
        pendaftaran.setIdUser(id);
        pendaftaran.setCreaby(user.getEmail());
        pendaftaran.setCreadate(LocalDateTime.now());
        pendaftaran.setModidate(LocalDateTime.now());
        pendaftaran.setModiby("");
        pendaftaran.setIdStatus(2);
        pendaftaran.setNotifikasi(0);
        pendaftaran.setStatus(1);
        if(trDaftarTimService.save(pendaftaran)){
            model.addAttribute("info_pendaftaran", true);
        }else{
            model.addAttribute("info_pendaftaran", false);
        }
        return "page/index";
    }
}
