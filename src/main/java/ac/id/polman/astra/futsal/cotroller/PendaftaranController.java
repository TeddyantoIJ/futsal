package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.*;
import ac.id.polman.astra.futsal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    public String Admin(Model model, HttpSession session, ModelMap modelMap) {
        String nama = (String) session.getAttribute("nama_depan");

        return "template/dashboard_admin";
    }

    @PostMapping("/Logincek")
    public String Logincek(@RequestParam(name = "username") String username,
                           @RequestParam(name = "password") String password,
                           HttpSession session) {

        MsAkun msAkun = akunService.getUserByUsername(username);
        if (msAkun == null) {
            return "redirect:/page-login";
        }

        if (msAkun.getUsername().equals(username) && msAkun.getPassword().equals(password)) {
            MsUser msUser = userService.getUserByIdAkun(msAkun.getIdAkun());
            MsMerchant msMerchant = merchantService.getMerchantByIdUser(msUser.getIdUser());
            MsTim msTim = timService.getTimByIdUser(msUser.getIdUser());

            session.setAttribute("merchant", msMerchant);
            session.setAttribute("login", true);
            session.setAttribute("id_user", msUser.getIdUser());
            session.setAttribute("user", msUser);
            session.setAttribute("tim", msTim);
            if (msAkun.getIdRole() != 1) {
                return "redirect:/";
            } else {
                return "redirect:/Admin";
            }
        } else {
            return "redirect:/page-login";
        }
    }

    @GetMapping("/Admin")
    public String goto_admin(
            HttpSession session, ModelMap modelMap, Model model
    ) {
        int id = -1;
        try {
            id = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        model.addAttribute("jmluser", userService.count());
        modelMap.addAttribute("jmltim", timService.getAllTim().size());
        model.addAttribute("jmlmerchant", merchantService.getAllMerchant().size());

        return "/template/dashboard_admin";
    }

    @RequestMapping("/addDaftarUser")
    public String addUser(MsUser msUser, MsAkun msAkun, HttpServletRequest request, Model model) {
        // Default 2 = User
        msAkun.setIdRole(2);
        msAkun.setCreaby(msUser.getEmail());
        msAkun.setCreadate(LocalDateTime.now());
        msAkun.setModiby("");
        msAkun.setModidate(LocalDateTime.now());
        msAkun.setStatus(1);
        if (!akunService.saveAkun(msAkun)) {
            model.addAttribute("user", msUser);
            model.addAttribute("notif", true);
            return "pendaftaran/adduser";
        }
        msUser.setIdAkun(akunService.getAllAkun().get(akunService.getAllAkun().size() - 1).getIdAkun());
        msUser.setFoto("");
        msUser.setCreaby(msUser.getEmail());
        msUser.setCreadate(LocalDateTime.now());
        msUser.setModiby("");
        msUser.setModidate(LocalDateTime.now());
        msUser.setStatus(1);

        userService.saveUser(msUser);

        return "redirect:/page-login";
    }

    //Pendaftaran TIM
    @GetMapping("/Daftar-Tim")
    public String getDaftarTim(Model model, TrDaftarTim trDaftartim, HttpSession session) {
        int id = -1;
        try {
            id = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        List<MsTim> a = timService.getAllTim();
        MsTim m = new MsTim();
        for (MsTim b : a) {
            if (b.getIdUser() == id) {
                m = b;
                break;
            }
        }

        List<TrDaftarTim> daftarList = daftarTimService.getAllIdTim(m.getIdTim());
        model.addAttribute("listTim", daftarList);

        List<MsUser> user = userService.getAllUser();
        model.addAttribute("listUser", user);

        List<MsStatus> status = statusService.getAllStatus();
        model.addAttribute("listStatus", status);

        return "page/konfirmasi_tim";
    }

    @GetMapping("/konfirmasi-Anggota")
    public String konfirmasiAnggota(@RequestParam("id") Integer id, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrDaftarTim trDaftartim = daftarTimService.getTimById(id);
        trDaftartim.setIdStatus(3);
        daftarTimService.saveTrDaftarTim(trDaftartim);

        MsUser a = userService.getUserById(trDaftartim.getIdUser());
        a.setIdUser(trDaftartim.getIdUser());
        a.setIdTim(2);
        userService.update(a);

        return "redirect:/Daftar-Tim";
    }

    @GetMapping("/Tolak-AnggotaTim")
    public String TolakAnggotaTim(@RequestParam("id") Integer id, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrDaftarTim trDaftartim = daftarTimService.getTimById(id);
        trDaftartim.setIdStatus(4);
        daftarTimService.saveTrDaftarTim(trDaftartim);

        return "redirect:/Daftar-Tim";
    }

    @GetMapping("/Tambah-AnggotaTim")
    public String TambahAnggotaTim(Model model, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        MsUser a = userService.getUserById(idus);

        List<MsUser> user = userService.getAllByTim(a.getIdTim());
        model.addAttribute("listUser", user);

        return "page/tambah_anggota_tim";
    }

    @GetMapping("/Anggota_No_Tim")
    public String AnggotaNoTim(Model model, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        List<MsUser> user = userService.getAllByTimNUll();
        model.addAttribute("listUser", user);

        return "page/anggota_no_tim";
    }

    //tambah anggota tim pada anggota yang idtim null
    @GetMapping("/AnggotaTim-Tambah")
    public String TambahAnggotaTim(@RequestParam("id") Integer id, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        MsUser d = userService.getUserById(idus);

        MsUser c = userService.getUserById(id);
        c.setIdUser(id);
        c.setIdTim(d.getIdTim());
        userService.update(c);

        return "redirect:/Anggota_No_Tim";
    }

    //hapus anggota pada tim
    @GetMapping("/AnggotaTim-Hapus")
    public String HapusAnggotaTim(@RequestParam("id") Integer id, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        MsUser d = userService.getUserById(idus);

        MsUser c = userService.getUserById(id);
        c.setIdUser(id);
        c.setIdTim(null);
        userService.update(c);

        return "redirect:/Tambah-AnggotaTim";
    }
}
