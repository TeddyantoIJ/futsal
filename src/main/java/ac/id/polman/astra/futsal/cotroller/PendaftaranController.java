package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsAkun;
import ac.id.polman.astra.futsal.model.MsMerchant;
import ac.id.polman.astra.futsal.model.MsUser;
import ac.id.polman.astra.futsal.service.AkunService;
import ac.id.polman.astra.futsal.service.MerchantService;
import ac.id.polman.astra.futsal.service.UserService;
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
            return "/Login";
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
            return "Login";
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
}
