package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsMerchant;
import ac.id.polman.astra.futsal.service.MerchantService;
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
public class MerchantController {
    @Autowired
    MerchantService merchantService;

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

    // ================================================================
    @PostMapping("/addMerchant")
    public String addMerchant(
            MsMerchant msMerchant,
            @RequestParam("file") MultipartFile file,
            @RequestParam("file1") MultipartFile file1){
        UploadController uploadController = new UploadController();

        String foto = uploadController.uploadFotoMerchant(file, "none");
        String banner = uploadController.uploadBannerMerchant(file1, "none");


        msMerchant.setId_user(1);
        msMerchant.setFoto(foto);
        msMerchant.setBanner(banner);
        msMerchant.setCreaby("Teddy(harusnya ambil nama yang bikin)");
        msMerchant.setCreadate(LocalDateTime.now());
        msMerchant.setModiby("");
        msMerchant.setModidate(LocalDateTime.now());
        msMerchant.setStatus(1);
        msMerchant.setRating(0f);

//        System.out.println(nama);
        merchantService.saveMerchant(msMerchant);
        return "redirect:/Merchant";
    }

    @PostMapping("/editMerchant")
    public String editMerchant(
        MsMerchant msMerchant,
        @RequestParam("file") MultipartFile file,
        @RequestParam("file1") MultipartFile file1
    ){
        UploadController uploadController = new UploadController();
        MsMerchant oldMerchant = merchantService.getMerchantById(msMerchant.getId_merchant());

        msMerchant.setId_user(oldMerchant.getId_user());
        msMerchant.setCreaby(oldMerchant.getCreaby());
        msMerchant.setCreadate(oldMerchant.getCreadate());
        msMerchant.setModiby("yang login sekarang");
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
        return "redirect:/Merchant";
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
}
