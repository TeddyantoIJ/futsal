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

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/User")
    public String getUser(Model model){
        List<MsUser> data = userService.findAllByStatus(1);
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
        msUser.setCreaby(msUser.getEmail());
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
        msUser.setModiby(oldUser.getEmail());
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

    @PostMapping("/edit-user-1")
    public String editUser1(
            HttpSession session,
            MsUser msUser,
            @RequestParam("file") MultipartFile file
    ){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        MsUser a = userService.getUserById(id);
        UploadController uploadController = new UploadController();
        MsUser oldUser = userService.getUserById(msUser.getIdUser());
        System.out.println(oldUser.getEmail());
        msUser.setCreaby(oldUser.getCreaby());
        msUser.setCreadate(oldUser.getCreadate());
        msUser.setModiby(a.getEmail());
        msUser.setModidate(LocalDateTime.now());
        msUser.setStatus(oldUser.getStatus());
        msUser.setIdAkun(oldUser.getIdAkun());
        msUser.setIdTim(oldUser.getIdTim());
        String foto;

        if(!file.isEmpty()){
            foto = uploadController.uploadFotoProfile(file, oldUser.getFoto());
            msUser.setFoto(foto);
        }else{
            msUser.setFoto(oldUser.getFoto());
        }

        userService.update(msUser);
        return "redirect:/my-profile";
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
