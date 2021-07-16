package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.*;
import ac.id.polman.astra.futsal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AjakTandingController {
    @Autowired
    TimService timService;

    @Autowired
    LapanganService lapanganService;

    @Autowired
    StatusService statusService;

    @Autowired
    AjakTandingService ajakTandingService;

    @Autowired
    UserService userService;

    @Autowired
    FasilitasService fasilitasService;

    @GetMapping("/Ajak-Tanding")
    public String getAjakTim(Model model, HttpSession session){
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        MsUser a = userService.getUserById(idus);

        List<TrAjakTanding> ajakList = ajakTandingService.getAllByTim(a.getIdTim());
        model.addAttribute("listAjak", ajakList);

        List<MsStatus> status = statusService.getAllStatus();
        model.addAttribute("listStatus", status);

        List<MsTim> tim = timService.getAllTim();
        model.addAttribute("listTim", tim);

        List<MsLapangan> lapangan = lapanganService.getAllLapangan();
        model.addAttribute("listLap", lapangan);

        return "page/ajak_tanding";
    }

    @GetMapping("/Add-AjakTim")
    public String getAddTim(Model model, HttpSession session){
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }
        List<MsTim> data = timService.getAllTim();
        List<MsTim> msTimList = new ArrayList<>();
        for ( MsTim msTim : data )
        {
            if(msTim.getStatus() == 1){
                msTimList.add(msTim);
            }
        }

        model.addAttribute("listTim", msTimList);
        return "page/add_ajak_tim";
    }

    @GetMapping("/Tim-AjakTanding")
    public String gotoAjakTim(Model model, @RequestParam(name = "id_tim") int id_tim, HttpSession session){
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        MsTim msTim = new MsTim();
        msTim = timService.getTimById(id_tim);
        List<MsLapangan> lapangan = lapanganService.getAllLapangan();

        model.addAttribute("listLap", lapangan);
        model.addAttribute("timObj", msTim);
        return "page/input_ajak_tim";
    }

    @PostMapping("/addAjakTim")
    public String addAjakTim(TrAjakTanding trAjakTanding, HttpSession session,
                             @RequestParam("waktu") String waktu, @RequestParam("id_tim2") Integer id_tim2){
        int idus = -1;
        try{
            idus = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        MsUser a = userService.getUserById(idus);
        trAjakTanding.setIdTim1(a.getIdTim());
        trAjakTanding.setIdTim2(id_tim2);
        trAjakTanding.setJam(new Time(Integer.parseInt(waktu.split(":")[0]),0,0));
        trAjakTanding.setCreaby(a.getNamaDepan());
        trAjakTanding.setId_status(2);
        trAjakTanding.setCreadate(LocalDateTime.now());
        trAjakTanding.setModiby("");
        trAjakTanding.setModidate(LocalDateTime.now());
        trAjakTanding.setStatus(1);
        ajakTandingService.saveTim(trAjakTanding);

        return "redirect:/Ajak-Tanding";
    }

    @GetMapping("/AjakTim-Batal")
    public String BatalAjakTim(@RequestParam("id") Integer id, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrAjakTanding trAjakTanding = ajakTandingService.getAllById(id);
        trAjakTanding.setId_status(7);
        ajakTandingService.saveTim(trAjakTanding);

        return "redirect:/Ajak-Tanding";
    }

}
