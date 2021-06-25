package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsUser;
import ac.id.polman.astra.futsal.service.UserService;
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
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/User")
    public String getUser(Model model){
        List<MsUser> data = userService.getAllUser();
        List<MsUser> msUserList = new ArrayList<>();
        for ( MsUser msUser : data )
        {
            if(msUser.getStatus() == 1){
                msUserList.add(msUser);
            }
        }
//        model.addAttribute("listMerchant", msMerchantList);
        model.addAttribute("listUser", data);
        return "user/list";
    }

    @GetMapping("/User-add")
    public String gotoAdd(Model model){
        model.addAttribute("userObj", new MsUser());
        return "user/add";
    }

    @GetMapping("/User-edit")
    public String gotoEdit(Model model, @RequestParam(name = "id_user") int id_user){
        MsUser msUser = new MsUser();
        msUser = userService.getUserById(id_user);
        model.addAttribute("userObj", msUser);
        return "user/edit";
    }

    @GetMapping("/User-detail")
    public String gotDetail(Model model, @RequestParam(name = "id_user") int id_user){
        MsUser msUser = new MsUser();
        msUser = userService.getUserById(id_user);
        model.addAttribute("userObj", msUser);
        return "user/detail";
    }

    // ================================================================
    @PostMapping("/addUser")
    public String addUser(
            MsUser msUser,
            @RequestParam("file") MultipartFile file){
        UploadController uploadController = new UploadController();

        String foto = uploadController.uploadFotoMerchant(file, "none");

//        msUser.setTanggalLahir(LocalDateTime.now());
        msUser.setIdAkun(1);
        msUser.setFoto(foto);
        msUser.setCreaby("Teddy(harusnya ambil nama yang bikin)");
        msUser.setCreadate(LocalDateTime.now());
        msUser.setModiby("");
        msUser.setModidate(LocalDateTime.now());
        msUser.setStatus(1);

        userService.saveUser(msUser);
        return "redirect:/User";
    }

    @PostMapping("/editUser")
    public String editUser(
            MsUser msUser,
            @RequestParam("file") MultipartFile file
    ){
        UploadController uploadController = new UploadController();
        MsUser oldUser = userService.getUserById(msUser.getIdUser());

        msUser.setCreaby(oldUser.getCreaby());
        msUser.setCreadate(oldUser.getCreadate());
        msUser.setModiby("yang login sekarang");
        msUser.setModidate(LocalDateTime.now());
        msUser.setStatus(oldUser.getStatus());

        String foto;

        if(!file.isEmpty()){
            foto = uploadController.uploadFotoMerchant(file, oldUser.getFoto());
            msUser.setFoto(foto);
        }else{
            msUser.setFoto(oldUser.getFoto());
        }

        userService.update(msUser);
        return "redirect:/User";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(
            @RequestParam("id_user") Integer id_user
    ){
        MsUser msUser = userService.getUserById(id_user);
        msUser.setStatus(0);
        userService.update(msUser);
        return "redirect:/User";
    }
}
