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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    TimService timService;
    @Autowired
    Tr_jadwal_lapangan_service trJadwalLapanganService;
    @Autowired
    AkunService akunService;
    @Autowired
    Tr_booking_lapangan_service trBookinglapanganservice;

    UploadController uploadController = new UploadController();

    @GetMapping("/")
    public String getIndex(
            HttpSession session,
            Model model
    ){

        try{
            if((boolean) session.getAttribute("login")){
                int id = -1;

                id = (int) session.getAttribute("id_user");
                int bill = get_notif_bill(id);
                int merchant = get_notif_merchant(id);

                int notif = bill + merchant;

                session.setAttribute("bill", bill);
                session.setAttribute("notif_merchant", merchant);

                if(notif > 0){
                    session.setAttribute("notif", true);
                }else{
                    session .setAttribute("notif", false);
                }

            }
        }catch (Exception e){

            session.setAttribute("login", false);
        }
        //merchant
        List<MsMerchant> merchantList = merchantService.get6MerchantActive();
        //tim
        List<MsTim> data = timService.getAllTim();
        List<MsTim> msTimList = new ArrayList<>();
        for ( MsTim msTim : data )
        {
            if(msTim.getStatus() == 1){
                msTimList.add(msTim);
            }
        }

        model.addAttribute("Timlist", data);
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
        session.removeAttribute("login");
        session.removeAttribute("merchant");
        session.removeAttribute("id_user");
        session.removeAttribute("user");
        session.removeAttribute("tim");
        session.removeAttribute("bill");
        session.removeAttribute("notif");
        session.removeAttribute("notif_merchant");
        session.removeAttribute("booking_order");

        return "redirect:/";
    }

    @GetMapping("/about-ivte-p")
    public String about_ivte_p(){
        return "";
    }

    // ======================================= Profil ======================================
    @GetMapping("/my-profile")
    public String goto_my_profile(
            HttpSession session,
            Model model
    ){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        MsUser a = userService.getUserById(id);
        MsAkun b = akunService.getAkunByIdAkun(a.getIdAkun());
        MsMerchant c = merchantService.getMerchantByIdUser(id);
        MsTim d = timService.getTimByIdUser(id);

        model.addAttribute("user", a);
        model.addAttribute("akun", b);
        model.addAttribute("merchant", c);
        model.addAttribute("tim", d);
        return "/page/profile";
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
    public String goto_merchant_create(
            Model model,
            HttpSession session)
    {
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        List<MsBiaya> biayaList = biayaService.getAllBiaya();
        model.addAttribute("biayaList", biayaList);
        return "merchant/add";
    }

    @GetMapping("/my-merchant")
    public String goto_my_merchant(
            Model model,
            HttpSession session){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }

        MsMerchant m = merchantService.getMerchantByIdUser(id);
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

        session.setAttribute("booking_order", trBookinglapanganservice.getAllDiprosesByIdMerchant(m.getId_merchant()).size());
        return "page/merchant_index";
    }

    @GetMapping("/my-merchant-booking-order")
    public String goto_my_merchant_booking_order(
            Model model,
            HttpSession session
    ){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e) {
            return "redirect:/page-login";
        }

        int idMerchant = merchantService.getMerchantByIdUser(id).getId_merchant();
        List<TrBookingLapangan> a = trBookinglapanganservice.getAllDiprosesByIdMerchant(idMerchant);
        List<MsLapangan> b = lapanganService.getAllLapanganByIdMerchant(idMerchant);
        List<MsTim> c = timService.getAllTim();

        session.setAttribute("booking_order", a.size());

        model.addAttribute("booking", a);
        model.addAttribute("lapangan", b);
        model.addAttribute("tim",c);
        return "/page/merchant_booking_order";
    }

    @GetMapping("/merchant-search/{id}")
    public String goto_merchant_id(
            @PathVariable int id,
            Model model,
            HttpSession session
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

    @GetMapping("/detail-lapangan/{id}")
    public String goto_detail_lapangan(
            @PathVariable int id,
            Model model,
            @RequestParam("tanggal") Optional<String> tanggal,
            HttpSession session
    ){
        MsLapangan msLapangan = lapanganService.getLapanganByIdLapangan(id);
        MsMerchant msMerchant = merchantService.getMerchantById(msLapangan.getId_merchant());
        List<DtFotolapangan> a = dtFotoLapanganService.getAllDtFotoLapanganByIdLapangan(id);
        List<MsTim> t = timService.getAllActive();
        List<TrJadwalLapangan> j = new ArrayList<>();
        if(!tanggal.isPresent() || tanggal.get().equals("")){
            j = trJadwalLapanganService.getAllNextByIdLapangan(id);
        }else{
            j = trJadwalLapanganService.getAllByIdAndByDate(id, tanggal.get());
        }

        model.addAttribute("merchant", msMerchant);
        model.addAttribute("lapangan", msLapangan);
        model.addAttribute("fotLap", a);
        model.addAttribute("tim", t);
        model.addAttribute("jadwal", j);

        try{
            model.addAttribute("tanggal", tanggal.get());
        }catch (Exception e){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            model.addAttribute("tanggal", sdf.format(new Date()));
        }

        return "page/merchant_lapangan_detail";
    }


    @PostMapping("/booking")
    public String booking(
            TrBookingLapangan a,
            HttpSession session,
            @RequestParam("waktu") String waktu
    ){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        MsUser us = userService.getUserById(id);
        MsLapangan la = lapanganService.getLapanganByIdLapangan(a.getId_lapangan());

        a.setJam(new Time(Integer.parseInt(waktu.split(":")[0]),0,0));
        a.setId_status(1);
        a.setIdTim(us.getIdTim());
        a.setNotifikasi(0);
        if(Integer.parseInt(waktu.split(":")[0]) > 16){
            a.setDp((int) (la.getHarga() * a.getDurasi1()) / 2);
        }else{
            a.setDp((int) (la.getHarga_penerangan() * a.getDurasi1()) / 2);
        }
        a.setSub_harga((int) (la.getHarga() * a.getDurasi1()));
        a.setBukti_transfer("");
        a.setCreaby(us.getEmail());
        a.setCreadate(us.getCreadate());
        a.setModiby(us.getEmail());
        a.setModidate(LocalDateTime.now());
        a.setStatus(1);

        if(trBookinglapanganservice.save(a)){
            return "redirect:/";
        }else{
            return "redirect:/detail-lapangan/"+la.getIdLapangan();
        }
    }

    @GetMapping("/konfirmasi-booking-valid/{id}")
    public String konfirmasi_booking_valid(
            @PathVariable int id,
            HttpSession session
    ){
        int idUser = -1;
        try{
            idUser = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        MsUser us = userService.getUserById(idUser);
        TrBookingLapangan a = trBookinglapanganservice.getById(id);
        a.setModiby(us.getEmail());
        a.setModidate(LocalDateTime.now());
        trBookinglapanganservice.update_terkonfirmasi(a);

        return "redirect:/my-merchant-booking-order";
    }

    @GetMapping("/konfirmasi-booking-invalid/{id}")
    public String konfirmasi_booking_invalid(
            @PathVariable int id,
            HttpSession session
    ){
        int idUser = -1;
        try{
            idUser = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        MsUser us = userService.getUserById(idUser);
        TrBookingLapangan a = trBookinglapanganservice.getById(id);
        a.setModiby(us.getEmail());
        a.setModidate(LocalDateTime.now());
        trBookinglapanganservice.update_gagal(a);

        return "redirect:/my-merchant-booking-order";
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

        List<MsTim> a = timService.getAllTim();
        MsTim m = new MsTim();
        for ( MsTim b: a) {
            if(b.getIdUser() == id){
                m = b;
                break;
            }
        }

        model.addAttribute("tim", m);

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
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        TrPendaftaranMerchant pendaftaranMerchant = pendaftaran_merchant_service.getByIdUser(id);
        List<MsStatus> st = statusService.getAllStatus();
        List<MsBiaya> by = biayaService.getAllBiaya();
        List<TrBookingLapangan> tb = new ArrayList<>();
        try{
            tb = trBookinglapanganservice.getAllMenungguPembayaranByIdTim(userService.getUserById(id).getIdTim());
        }catch (Exception e){

        }

        List<MsLapangan> lp = lapanganService.getAllLapangan();

        model.addAttribute("bill_pendaftaran", pendaftaranMerchant);
        model.addAttribute("bill_booking", tb);
        model.addAttribute("status", st);
        model.addAttribute("biaya", by);
        model.addAttribute("lapangan", lp);

        return "page/bill";
    }

    @PostMapping("/bayar_pendaftaran")
    public String bayar_pendaftaran(
            @RequestParam("bukti_transfer") MultipartFile file,
            @RequestParam("id") int id){

        TrPendaftaranMerchant a = pendaftaran_merchant_service.getPendaftaran(id);
        a.setModidate(LocalDateTime.now());
        a.setBukti_transfer(uploadController.upload_bukti_tf(file, "none"));
        a.setId_status(5);
        pendaftaran_merchant_service.save(a);
        return "redirect:/bill";
    }

    @PostMapping("/bayar_booking")
    public String bayar_booking(
            @RequestParam("bukti_transfer") MultipartFile file,
            @RequestParam("id") int id){

        TrBookingLapangan a = trBookinglapanganservice.getById(id);
        a.setModidate(LocalDateTime.now());
        a.setBukti_transfer(uploadController.upload_bukti_tf(file, "none"));
        a.setId_status(2);
        if(trBookinglapanganservice.save(a)){
            return "redirect:/";
        }else{
            uploadController.hapus_bukti_tf(a.getBukti_transfer());
            return "redirect:/bill";
        }
    }

    @GetMapping("/batal_booking/{id}")
    public String batal_booking(
            @PathVariable int id)
    {
        TrBookingLapangan a = trBookinglapanganservice.getById(id);
        trBookinglapanganservice.update_batal(a);
        return "redirect:/";
    }

    public int get_notif_bill(int id){
        int out = 0;
        int bill_booking = 0;
        int pendaftaran = 0;
        try {
            bill_booking = trBookinglapanganservice.getAllMenungguPembayaranByIdTim(userService.getUserById(id).getIdTim()).size();
        }catch (Exception e){
        }
        try{
            TrPendaftaranMerchant a = pendaftaran_merchant_service.getByIdUser(id);
            if(a.getId_status() == 1){
                pendaftaran = 1;
            }
        }catch (Exception e){

        }
        out = bill_booking + pendaftaran;
        return out;
    }

    public int get_notif_merchant(int id){

        try{
            return trBookinglapanganservice.getAllDiprosesByIdMerchant(merchantService.getMerchantByIdUser(id).getId_merchant()).size();
        }catch (Exception e){
            return 0;
        }
    }
}
