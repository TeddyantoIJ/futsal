package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.*;
import ac.id.polman.astra.futsal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PendaftaranController {
    @Autowired
    UserService userService;
    @Autowired
    AkunService akunService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    DaftarTimService daftarTimService;
    @Autowired
    StatusService statusService;
    @Autowired
    TimService timService;

    @GetMapping("/MenuAdmin")
    public String Admin(Model model){
        return "template/admines";
    }

    @PostMapping("/Logincek")
    public String Logincek(@RequestParam(name = "username") String username,
                           @RequestParam(name = "password") String password,
                           HttpSession session) {

        MsAkun msAkun = akunService.getUserByUsername(username);

        if(msAkun == null){
            return "redirect:/page-login";
        }

        if (msAkun.getUsername().equals(username) && msAkun.getPassword().equals(password)){
            MsUser msUser = userService.getUserByIdAkun(msAkun.getIdAkun());

            if(msAkun.getIdRole() != 1){
                List<MsMerchant> msMerchant = merchantService.getAllMerchant();
                session.setAttribute("hasMerchant", false);
                for (MsMerchant a : msMerchant)
                {
                    if(a.getId_user() == msUser.getIdUser()){
                        session.setAttribute("hasMerchant", true);
                        break;
                    }
                }
                session.setAttribute("login", true);
                session.setAttribute("id_user", msUser.getIdUser());

                return "/page/index";
            }else{
                session.setAttribute("login", true);
                session.setAttribute("id_user", msUser.getIdUser());
                return "/template/admines";
            }
        }else{
            return "redirect:/page-login";
        }
    }

    @PostMapping("/addDaftarUser")
    public String addUser(MsUser msUser, MsAkun msAkun) {
        // Default 2 = User
        msAkun.setIdRole(2);
        msAkun.setCreaby("user");
        msAkun.setCreadate(LocalDateTime.now());
        msAkun.setModiby("");
        msAkun.setModidate(LocalDateTime.now());
        msAkun.setStatus(1);
        akunService.saveAkun(msAkun);

        msUser.setIdAkun(akunService.getAllAkun().get(akunService.getAllAkun().size()-1).getIdAkun());
        msUser.setFoto("");
        msUser.setCreaby("user");
        msUser.setCreadate(LocalDateTime.now());
        msUser.setModiby("");
        msUser.setModidate(LocalDateTime.now());
        msUser.setStatus(1);
        userService.saveUser(msUser);

        return "Login";
    }

    //Pendaftaran TIM
    @GetMapping("/Daftar-Tim")
    public String getDaftarTim(Model model, TrDaftarTim trDaftartim){
        List<TrDaftarTim> daftarList = daftarTimService.getAllIdTim(1);
        model.addAttribute("listTim", daftarList);

        MsUser msUser = new MsUser();
        msUser = userService.getUserById(1);
        model.addAttribute("userObj", msUser);

        MsStatus msStatus = statusService.getStatus(1);
        model.addAttribute("msStatus", msStatus);

        return "page/konfirmasi_tim";
    }

    @GetMapping("/konfirmasi-Anggota")
    public String konfirmasiAnggota(@RequestParam("id") Integer id, HttpSession session){
        int idus = -1;
        try{
            idus = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }

        TrDaftarTim trDaftartim = daftarTimService.getTimById(id);
        trDaftartim.setIdStatus(2);
        daftarTimService.saveTrDaftarTim(trDaftartim);
        return "redirect:/Daftar-Tim";
    }

    //=========================DAFTAR ANGGOTA TIM
    @GetMapping("/AnggotaTim-add")
    public String gotoAdd(Model model){
        model.addAttribute("timObj", new TrDaftarTim());

        MsTim msTim = timService.getTimById(1);
        model.addAttribute("teamObj", msTim);

        MsUser msUser = new MsUser();
        msUser = userService.getUserById(1);
        model.addAttribute("userObj", msUser);
        return "pendaftaran/daftar_anggota_tim";
    }
}
