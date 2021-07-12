package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsAkun;
import ac.id.polman.astra.futsal.model.MsMerchant;
import ac.id.polman.astra.futsal.model.MsUser;
import ac.id.polman.astra.futsal.model.TrPendaftaranMerchant;
import ac.id.polman.astra.futsal.service.*;
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
public class MerchantController {
    @Autowired
    MerchantService merchantService;
    @Autowired
    Tr_Pendaftaran_Merchant_Service tr_pendaftaran_merchant_service;
    @Autowired
    StatusService statusService;
    @Autowired
    UserService userService;
    @Autowired
    BiayaService biayaService;
    @Autowired
    AkunService akunService;

    @GetMapping("/Merchant")
    public String getMerchant(Model model){
        List<MsMerchant> data = merchantService.getAllMerchant();
        List<MsMerchant> msMerchantList = new ArrayList<>();
        for ( MsMerchant msMerchant : data )
        {
            if(msMerchant.getStatus() == 1){
                msMerchantList.add(msMerchant);
            }
        }
//        model.addAttribute("listMerchant", msMerchantList);
        model.addAttribute("listMerchant", data);
        return "merchant/list";
    }

    @GetMapping("/Merchant-add")
    public String gotoAdd(Model model){
        model.addAttribute("merchantObj", new MsMerchant());
        return "merchant/add";
    }

    @GetMapping("/Merchant-edit")
    public String gotoEdit(Model model, @RequestParam(name = "id_merchant") int id_merchant){
        MsMerchant msMerchant = new MsMerchant();
        msMerchant = merchantService.getMerchantById(id_merchant);
        model.addAttribute("merchantObj", msMerchant);
        return "merchant/edit";
    }

    @GetMapping("/Merchant-detail")
    public String gotDetail(Model model, @RequestParam(name = "id_merchant") int id_merchant){
        MsMerchant msMerchant = new MsMerchant();
        msMerchant = merchantService.getMerchantById(id_merchant);
        model.addAttribute("merchantObj", msMerchant);

        return "merchant/detail";
    }

    @GetMapping("/konfirmasi-merchant")
    public String goto_konfirmasi(Model model){
        List<TrPendaftaranMerchant> pendaftaranMerchantList = tr_pendaftaran_merchant_service.getAll();
        List<MsMerchant> listMsMerchant = merchantService.getAllMerchant();
        model.addAttribute("list_pendaftaran", pendaftaranMerchantList);
        model.addAttribute("listMerchang", listMsMerchant);
        return "/page/admin_konfirmasi_merchant";
    }
    // ================================================================
    @PostMapping("/addMerchant")
    public String addMerchant(
            HttpSession session,
            MsMerchant msMerchant,
            @RequestParam("file") MultipartFile file,
            @RequestParam("file1") MultipartFile file1,
            @RequestParam("nominal") int nominal){
        UploadController uploadController = new UploadController();
        MsUser user = userService.getUserById((Integer) session.getAttribute("id_user"));

        String foto = uploadController.uploadFotoMerchant(file, "none");
        String banner = uploadController.uploadBannerMerchant(file1, "none");


        msMerchant.setId_user((int) session.getAttribute("user_id"));
        msMerchant.setFoto(foto);
        msMerchant.setBanner(banner);
        msMerchant.setCreaby(user.getEmail());
        msMerchant.setCreadate(LocalDateTime.now());
        msMerchant.setModiby("");
        msMerchant.setModidate(LocalDateTime.now());
        msMerchant.setStatus(0);
        msMerchant.setRating(0f);

//        System.out.println(nama);
        merchantService.saveMerchant(msMerchant);

        TrPendaftaranMerchant pend = new TrPendaftaranMerchant();
        pend.setCreaby(user.getEmail());
        pend.setCreadate(LocalDateTime.now());
        pend.setId_merchant(merchantService.getAllMerchant().get(merchantService.getAllMerchant().size()-1).getId_merchant());
        pend.setModiby("");
        pend.setModidate(LocalDateTime.now());
        pend.setStatus(1);
        pend.setId_status(1);
        pend.setId_biaya(nominal);
        pend.setNominal(biayaService.getBiaya(nominal).getNominal());
        pend.setNotifikasi(0);

        tr_pendaftaran_merchant_service.save(pend);
        return "/page/index";
    }

    @PostMapping("/editMerchant")
    public String editMerchant(
        MsMerchant msMerchant,
        @RequestParam("file") MultipartFile file,
        @RequestParam("file1") MultipartFile file1,
        HttpSession session
    ){
        UploadController uploadController = new UploadController();
        MsMerchant oldMerchant = merchantService.getMerchantById(msMerchant.getId_merchant());
        MsUser a = new MsUser();
        try {
            a = userService.getUserById((int) session.getAttribute("id_user"));
        }catch (Exception e){
            return "redirect:/page-login";
        }
        msMerchant.setId_user(oldMerchant.getId_user());
        msMerchant.setCreaby(oldMerchant.getCreaby());
        msMerchant.setCreadate(oldMerchant.getCreadate());
        msMerchant.setModiby(a.getEmail());
        msMerchant.setModidate(LocalDateTime.now());
        msMerchant.setStatus(oldMerchant.getStatus());
        msMerchant.setRating(oldMerchant.getRating());

        String foto;
        String banner;

        if(!file.isEmpty()){
            foto = uploadController.uploadFotoMerchant(file, oldMerchant.getFoto());
            msMerchant.setFoto(foto);
        }else{
            msMerchant.setFoto(oldMerchant.getFoto());
        }

        if(!file1.isEmpty()){
            banner = uploadController.uploadBannerMerchant(file1, oldMerchant.getBanner());
            msMerchant.setBanner(banner);
        }else{
            msMerchant.setBanner(oldMerchant.getBanner());
        }

        merchantService.update(msMerchant);
        return "redirect:/my-merchant";
    }

    @GetMapping("/deleteMerchant")
    public String deleteMerchant(
            @RequestParam("id_merchant") Integer id_merchant
    ){
        MsMerchant msMerchant = merchantService.getMerchantById(id_merchant);
        msMerchant.setStatus(0);
        merchantService.update(msMerchant);
        return "redirect:/Merchant";
    }

    @GetMapping("/konfirmasi-merchant/c")
    public String konfirmasi_merchant_c(
            @RequestParam("id") int id,
            HttpSession session
    ){
        TrPendaftaranMerchant a = tr_pendaftaran_merchant_service.getPendaftaran(id);
        try{
            a.setModiby(userService.getUserById((int) session.getAttribute("id_user")).getEmail());
        }catch (Exception e){
            return "redirect:/page-login";
        }
        a.setModiby(userService.getUserById((int) session.getAttribute("id_user")).getEmail());
        a.setModidate(LocalDateTime.now());
        a.setId_status(3);

        tr_pendaftaran_merchant_service.save(a);

        MsMerchant b = merchantService.getMerchantById(a.getId_merchant());
        b.setStatus(1);
        merchantService.saveMerchant(b);

        MsAkun c = akunService.getAkunByIdAkun((userService.getUserById(b.getId_user())).getIdAkun());
        c.setIdRole(3);
        akunService.saveAkun(c);

        return "redirect:/konfirmasi-merchant";
    }
    @GetMapping("/konfirmasi-merchant/r")
    public String konfirmasi_merchant_r(
            @RequestParam("id") int id,
            HttpSession session
    ){
        TrPendaftaranMerchant a = tr_pendaftaran_merchant_service.getPendaftaran(id);
        a.setModiby(userService.getUserById((int) session.getAttribute("id_user")).getEmail());
        a.setModidate(LocalDateTime.now());
        a.setStatus(4);

        tr_pendaftaran_merchant_service.save(a);

        return "redirect:/konfirmasi-merchant";
    }
}
