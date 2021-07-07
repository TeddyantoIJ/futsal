package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.*;
import ac.id.polman.astra.futsal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RootController {

    @Autowired
    BiayaService biayaService;
    @Autowired
    Tr_Pendaftaran_Merchant_Service pendaftaran_merchant_service;
    @Autowired
    StatusService statusService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    LapanganService lapanganService;
    @Autowired
    FasilitasService fasilitasService;
    @Autowired
    DtMerchantService dtMerchantService;
    @Autowired
    DtFotoLapanganService dtFotoLapanganService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserService userService;

    UploadController uploadController = new UploadController();

    @GetMapping("/")
    public String getIndex(
            HttpSession session,
            Model model
    ){

        try{
            if((boolean) session.getAttribute("login")){

            }
        }catch (Exception e){
            session.setAttribute("login", false);
        }
        List<MsMerchant> merchantList = merchantService.get6MerchantActive();


        model.addAttribute("merchantList", merchantList);
        return "page/index";
    }

    @GetMapping("/IVTE")
    public String getIvte(){
        return "page/Index";
    }

    @GetMapping("/page-registration")
    public String gotoAdd(Model model){
        model.addAttribute("userObj", new MsUser());
        model.addAttribute("akunObj", new MsAkun());
        return "pendaftaran/adduser";
//        return "template/login";
    }

    @GetMapping("/page-login")
    public String Login(Model model){
        model.addAttribute("akunObj", new MsAkun());
        return "template/login";
    }

    @GetMapping("/Logout")
    public String Logout(HttpSession session){
        session.setAttribute("login", false);
        session.removeAttribute("id_user");
        return "page/Index";
    }

    @GetMapping("/about-ivte-p")
    public String about_ivte_p(){
        return "";
    }

    // ======================================= Profil ======================================
    @GetMapping("/my-profil")
    public String goto_my_profil(){
        return "";
    }

    // ======================================= MERCHANT ======================================
    @GetMapping("/merchant-show-all")
    public String goto_merchant_show_all(
            Model model,
            HttpSession session,
            @RequestParam("search") Optional<String> search
    )
    {
        try{
            if((boolean) session.getAttribute("login")){

            }
        }catch (Exception e){
            session.setAttribute("login", false);
        }

        List<MsMerchant> a = new ArrayList<>();
        if(!search.isPresent() || search.get().equals("")){
            a = merchantService.getAllActive();
        }else{
            a = merchantService.getMerchantByName(search.get());
        }

        model.addAttribute("merchantList", a);
        return "page/all_merchant";
    }


    @GetMapping("/merchant-create")
    public String goto_merchant_create(Model model)
    {
        List<MsBiaya> biayaList = biayaService.getAllBiaya();
        model.addAttribute("biayaList", biayaList);
        return "merchant/add";
    }

    @GetMapping("/my-merchant")
    public String goto_my_merchant(Model model, HttpSession session){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }

        List<MsMerchant> a = merchantService.getAllMerchant();
        MsMerchant m = new MsMerchant();
        for ( MsMerchant b: a) {
            if(b.getId_user() == id){
                m = b;
                break;
            }
        }

        List<MsLapangan> lap = lapanganService.getAllLapanganByIdMerchant(m.getId_merchant());
        List<MsFasilitas> fasList = fasilitasService.getAllFacilities();
        List<DtMerchant> fasDit = dtMerchantService.getAllDtMerchantByIdMerchant(m.getId_merchant());
        List<DtFotolapangan> fotLap = new ArrayList<>();
        for (MsLapangan x : lap) {
            try{
                fotLap.add(dtFotoLapanganService.getAllDtFotoLapanganByIdLapangan(x.getIdLapangan()).get(0));
            }catch (Exception e){
                fotLap.add(new DtFotolapangan());
            }

        }
        List<TrReview> reviews = reviewService.getAllByIdMerchant(m.getId_merchant());
        List<MsUser> users = userService.getAllUser();

        model.addAttribute("merchant", m);
        model.addAttribute("lap",lap);
        model.addAttribute("fasList",fasList);
        model.addAttribute("fasDit",fasDit);
        model.addAttribute("fotLap",fotLap);
        model.addAttribute("reviews", reviews);
        model.addAttribute("users", users);

        session.setAttribute("nama_merchant", m.getNama());
        return "page/merchant_index";
    }

    @GetMapping("/merchant-search/{id}")
    public String goto_merchant_id(
            @PathVariable int id,
            Model model
    ){
        MsMerchant msMerchant = merchantService.getMerchantById(id);

        List<MsLapangan> lap = lapanganService.getAllLapanganByIdMerchant(msMerchant.getId_merchant());
        List<MsFasilitas> fasList = fasilitasService.getAllFacilities();
        List<DtMerchant> fasDit = dtMerchantService.getAllDtMerchantByIdMerchant(msMerchant.getId_merchant());
        List<DtFotolapangan> fotLap = new ArrayList<>();
        for (MsLapangan x : lap) {
            try{
                fotLap.add(dtFotoLapanganService.getAllDtFotoLapanganByIdLapangan(x.getIdLapangan()).get(0));
            }catch (Exception e){
                fotLap.add(new DtFotolapangan());
            }

        }
        List<TrReview> reviews = reviewService.getAllByIdMerchant(id);
        List<MsUser> users = userService.getAllUser();

        model.addAttribute("lap",lap);
        model.addAttribute("fasList",fasList);
        model.addAttribute("fasDit",fasDit);
        model.addAttribute("fotLap",fotLap);

        model.addAttribute("merchant", msMerchant);
        model.addAttribute("reviews", reviews);
        model.addAttribute("users", users);

        return "page/user_merchant_idx";
    }

    // ===================================MANAGER TIM ====================================

    @GetMapping("/my-tim")
    public String goto_my_timm(Model model, HttpSession session){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }

        List<MsMerchant> a = merchantService.getAllMerchant();
        MsMerchant m = new MsMerchant();
        for ( MsMerchant b: a) {
            if(b.getId_user() == id){
                m = b;
                break;
            }
        }

        List<MsLapangan> lap = lapanganService.getAllLapanganByIdMerchant(m.getId_merchant());
        List<MsFasilitas> fasList = fasilitasService.getAllFacilities();
        List<DtMerchant> fasDit = dtMerchantService.getAllDtMerchantByIdMerchant(m.getId_merchant());
        List<DtFotolapangan> fotLap = new ArrayList<>();
        for (MsLapangan x : lap) {
            fotLap.add(dtFotoLapanganService.getAllDtFotoLapanganByIdLapangan(x.getIdLapangan()).get(0));
        }

        model.addAttribute("merchant", m);
        model.addAttribute("lap",lap);
        model.addAttribute("fasList",fasList);
        model.addAttribute("fasDit",fasDit);
        model.addAttribute("fotLap",fotLap);

        return "page/manager_tim";
    }

    // ======================================= TEAM ======================================

    @GetMapping("/team-show-all")
    public String goto_team_show_all(){
        return "";
    }

    @GetMapping("/team-create")
    public String goto_team_create(){
        return "";
    }

    @GetMapping("/my-team")
    public String goto_my_team(){
        return "";
    }

    // ======================================= MATCH ======================================

    @GetMapping("/match-show-all")
    public String goto_match_show_all(){
        return "";
    }

    // ======================================= FRIENDLY MATCH ======================================

    @GetMapping("/friendly-match-show-all")
    public String goto_friendly_match_show_all(){
        return "";
    }

    @GetMapping("/ask-for-a-match")
    public String goto_ask_for_a_match(){
        return "";
    }

    // ======================================= PRACTICE ======================================

    @GetMapping("/practice-show-all")
    public String goto_practice_show_all(){
        return "";
    }

    // ======================================= BILL ======================================

    @GetMapping("/bill")
    public String goto_bill(Model model, HttpSession session){
        try{
            TrPendaftaranMerchant pendaftaranMerchant = pendaftaran_merchant_service.getByIdUser((int) session.getAttribute("id_user"));
            model.addAttribute("listBill", pendaftaranMerchant);
            model.addAttribute("status", statusService.getStatus(pendaftaranMerchant.getId_status()).getKeterangan());
            model.addAttribute("pembayaran", biayaService.getBiaya(pendaftaranMerchant.getId_biaya()).getKeterangan().split("/")[2].toUpperCase());
        }catch (Exception ex){

        }
        return "page/bill";
    }

    @PostMapping("bayar_pendaftaran")
    public String bayar_pendaftaran(
            @RequestParam("bukti_transfer") MultipartFile file,
            @RequestParam("id") int id){

        TrPendaftaranMerchant a = pendaftaran_merchant_service.getPendaftaran(id);
        a.setModidate(LocalDateTime.now());
        a.setBukti_transfer(uploadController.upload_bukti_tf(file));
        a.setId_status(2);
        pendaftaran_merchant_service.save(a);
        return "redirect:/bill";
    }
}
