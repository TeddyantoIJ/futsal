package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsAkun;
import ac.id.polman.astra.futsal.model.MsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String getIndex(){
        return "page/Index";
    }

    @GetMapping("/IVTE-P")
    public String getIvte(){
        return "page/Index";
    }

    @GetMapping("/IVTE-P/page-registration")
    public String gotoAdd(Model model){
        model.addAttribute("userObj", new MsUser());
        model.addAttribute("akunObj", new MsAkun());
        return "pendaftaran/adduser";
//        return "template/login";
    }

    @GetMapping("/IVTE-P/page-login")
    public String Login(Model model){
        model.addAttribute("akunObj", new MsAkun());
        return "template/login";
    }

    @GetMapping("/IVTE-P/Logout")
    public String Logout(){
        return "page/Index";
    }

    @GetMapping("/IVTE-P/about-ivte-p")
    public String about_ivte_p(){
        return "";
    }

    // ======================================= Profil ======================================
    @GetMapping("/IVTE-P/my-profil")
    public String goto_my_profil(){
        return "";
    }

    // ======================================= MERCHANT ======================================
    @GetMapping("/IVTE-P/merchant-show-all")
    public String goto_merchant_show_all(){
        return "";
    }

    @GetMapping("/IVTE-P/merchant-create")
    public String goto_merchant_create(){
        return "";
    }

    @GetMapping("/IVTE-P/my-merchant")
    public String goto_my_merchant(){
        return "";
    }

    // ======================================= TEAM ======================================

    @GetMapping("/IVTE-P/team-show-all")
    public String goto_team_show_all(){
        return "";
    }

    @GetMapping("/IVTE-P/team-create")
    public String goto_team_create(){
        return "";
    }

    @GetMapping("/IVTE-P/my-team")
    public String goto_my_team(){
        return "";
    }

    // ======================================= MATCH ======================================

    @GetMapping("/IVTE-P/match-show-all")
    public String goto_match_show_all(){
        return "";
    }

    // ======================================= FRIENDLY MATCH ======================================

    @GetMapping("/IVTE-P/friendly-match-show-all")
    public String goto_friendly_match_show_all(){
        return "";
    }

    @GetMapping("/IVTE-P/ask-for-a-match")
    public String goto_ask_for_a_match(){
        return "";
    }

    // ======================================= PRACTICE ======================================

    @GetMapping("/IVTE-P/practice-show-all")
    public String goto_practice_show_all(){
        return "";
    }

}
