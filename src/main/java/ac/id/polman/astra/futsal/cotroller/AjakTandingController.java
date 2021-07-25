package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.*;
import ac.id.polman.astra.futsal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    DtAjakTandingService dtAjakTandingService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    FasilitasService fasilitasService;

    @Autowired
    DtMerchantService dtMerchantService;

    @Autowired
    DtFotoLapanganService dtFotoLapanganService;

    @Autowired
    Tr_booking_lapangan_service tr_booking_lapangan_service;

    @Autowired
    Tr_jadwal_lapangan_service tr_jadwal_lapangan_service;

    @Autowired
    Tr_main_bareng_service trMainbarengservice;

    ///=============================== AJak Anggota ===============================////

    @GetMapping("/Add-AjakTim")
    public String getAddTim(Model model, HttpSession session,
                            @RequestParam("search") Optional<String> search){
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        List<MsTim> a = new ArrayList<>();
        if (!search.isPresent() || search.get().equals("")) {
        } else {
            a = timService.getTeamByName(search.get());
        }
        if(a==null){
            return "redirect:/Add-AjakTim";
        }

//        List<MsTim> data = timService.getAllTim();
//        List<MsTim> msTimList = new ArrayList<>();
//        for ( MsTim msTim : data )
//        {
//            if(msTim.getStatus() == 1){
//                msTimList.add(msTim);
//            }
//        }

        model.addAttribute("listTim", a);
        return "page/add_ajak_tim";
    }

    ///================================ AJak Tanding =============================///////
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
        List<MsMerchant> merchants = merchantService.getAllMerchant();

        model.addAttribute("listMerchant", merchants);
        model.addAttribute("listLap", lapangan);
        model.addAttribute("timObj", msTim);
        return "page/input_ajak_tim";
    }

    @PostMapping("/addAjakTim")
    public String addAjakTim(TrAjakTanding trAjakTanding, HttpSession session,
                             @RequestParam("waktu") String waktu, @RequestParam("id_tim2") Integer id_tim2,
                             @RequestParam("alasan") String alasan){
        int idus = -1;
        try{
            idus = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        MsUser a = userService.getUserById(idus);
        trAjakTanding.setIdTim1(a.getIdTim());
        trAjakTanding.setIdTim2(id_tim2);
        trAjakTanding.setAlasann(alasan);
        trAjakTanding.setId_lapangan(1);
        trAjakTanding.setJam(new Time(Integer.parseInt(waktu.split(":")[0]),0,0));
        trAjakTanding.setCreaby(a.getNamaDepan());
        trAjakTanding.setId_status(2);
        trAjakTanding.setCreadate(LocalDateTime.now());
        trAjakTanding.setModiby("");
        trAjakTanding.setModidate(LocalDateTime.now());
        trAjakTanding.setStatus(1);
        ajakTandingService.save(trAjakTanding);

//        return "redirect:/Ajak-Tanding";
        return "redirect:/AjakTanding-Merchant";
    }

    @GetMapping("/AjakTanding-Merchant")
    public String goto_merchant_show(
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
        return "page/ajak_tanding_merchant";
    }

    @GetMapping("/Ajak-merchant-search/{id}")
    public String goto_merchant_id(
            @PathVariable int id,
            Model model,
            HttpSession session
    ){
        int idus = -1;
        try{
            idus = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }

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
        List<MsUser> users = userService.getAllUser();

        model.addAttribute("lap",lap);
        model.addAttribute("fasList",fasList);
        model.addAttribute("fasDit",fasDit);
        model.addAttribute("fotLap",fotLap);

        model.addAttribute("merchant", msMerchant);
        model.addAttribute("users", users);

        return "page/ajak_tanding_lapangan";
    }

    @GetMapping("/AjakTim-Lapangan")
    public String UbahLapanganAjakTim(@RequestParam("id") Integer id, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrAjakTanding trAjakTanding = ajakTandingService.getLastId();
        trAjakTanding.setId_lapangan(id);
        ajakTandingService.save(trAjakTanding);

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
        trAjakTanding.setId_status(10);
        ajakTandingService.save(trAjakTanding);

        return "redirect:/Ajak-Tanding";
    }

    @GetMapping("/AjakTim-Detail")
    public String DetilAjakTim(@RequestParam("id") Integer id, HttpSession session, Model model) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrAjakTanding ajakList = ajakTandingService.getAllById(id);
        model.addAttribute("listAjak", ajakList);

        DtAjakTanding dtAjakTanding = dtAjakTandingService.getAllByAjakTandings(id);
        model.addAttribute("listdetail", dtAjakTanding);

        List<MsTim> tim = timService.getAllTim();
        model.addAttribute("listTim", tim);

        return "page/detail_ajak_tanding";
    }

    @GetMapping("/Ubah-DetilAjakTanding")
    public String UbahDetilAjakTim(@RequestParam("id") Integer id, HttpSession session, Model model) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrAjakTanding ajakList = ajakTandingService.getAllById(id);
        model.addAttribute("listAjak", ajakList);

        DtAjakTanding dtAjakTanding = dtAjakTandingService.getAllByAjakTandings(id);
        model.addAttribute("listdetail", dtAjakTanding);

        List<MsTim> tim = timService.getAllTim();
        model.addAttribute("listTim", tim);

        return "page/detail_ubah_ajak_tanding";
    }

    @PostMapping("/GetUbahDetailAjak")
    public String getUbahDetilAjakTim(@RequestParam("id") Integer id, HttpSession session,
                                      @RequestParam("skor") Integer skor, @RequestParam("skor2") Integer skor2,
                                      @RequestParam("juara") Integer juara) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        DtAjakTanding dtAjakTanding = dtAjakTandingService.getAllById(id);
        dtAjakTanding.setSkor(skor);
        dtAjakTanding.setSkor2(skor2);
        dtAjakTanding.setJuara(juara);
        dtAjakTandingService.save(dtAjakTanding);

        return "redirect:/AjakTim-Detail?id="+dtAjakTanding.getIdAjakTanding();
    }


    ///===================================Konfirmasi Ajak Tim=============================//

    @GetMapping("/Konfirmasi-Ajak-Tanding")
    public String getKonfirmasiAjakTim(Model model, HttpSession session){
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        MsUser a = userService.getUserById(idus);

        List<TrAjakTanding> ajakList = ajakTandingService.getAllByTim2(a.getIdTim());
        model.addAttribute("listAjak", ajakList);

        List<MsStatus> status = statusService.getAllStatus();
        model.addAttribute("listStatus", status);

        List<MsTim> tim = timService.getAllTim();
        model.addAttribute("listTim", tim);

        List<MsLapangan> lapangan = lapanganService.getAllLapangan();
        model.addAttribute("listLap", lapangan);

        return "page/konfirmasi_ajak_tanding";
    }

    public String convertDateToString(Date dt) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String dateToString = df.format(dt);
        return dateToString;
    }

    @GetMapping("/Konfirmasi-AjakTim")
    public String KonfirmAjakTim(@RequestParam("id") Integer id, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }
        MsUser user = userService.getUserById(idus);

        TrAjakTanding trAjakTanding = ajakTandingService.getAllById(id);
        trAjakTanding.setId_status(3);
        ajakTandingService.save(trAjakTanding);

        DtAjakTanding dtAjakTanding = new DtAjakTanding();
        dtAjakTanding.setIdAjakTanding(id);
        dtAjakTanding.setJuara(0);
        dtAjakTanding.setSkor(0);
        dtAjakTanding.setSkor2(0);
        dtAjakTanding.setCreaby(user.getNamaDepan());
        dtAjakTanding.setCreadate(LocalDateTime.now());
        dtAjakTanding.setModiby(user.getNamaDepan());
        dtAjakTanding.setModidate(LocalDateTime.now());
        dtAjakTanding.setStatus(1);
        dtAjakTandingService.save(dtAjakTanding);

        MsLapangan lapangan = lapanganService.getLapanganByIdLapangan(trAjakTanding.getId_lapangan());

        TrBookingLapangan trBookingLapangan = new TrBookingLapangan();
        trBookingLapangan.setIdTim(trAjakTanding.getIdTim1());
        trBookingLapangan.setId_lapangan(trAjakTanding.getId_lapangan());
        trBookingLapangan.setIdStatus(1);
        trBookingLapangan.setNotifikasi(0);
        trBookingLapangan.setTanggal(trAjakTanding.getTanggal());
        trBookingLapangan.setJam(trAjakTanding.getJam());
        trBookingLapangan.setDurasi(1);

        if(Integer.parseInt(convertDateToString(trAjakTanding.getJam()).split(":")[0]) > 16){
            trBookingLapangan.setDp((int) (lapangan.getHarga() * 1) / 2);
        }else{
            trBookingLapangan.setDp((int) (lapangan.getHarga_penerangan() * 1 / 2));
        }
        trBookingLapangan.setSub_harga((int) (lapangan.getHarga() * 1));
        trBookingLapangan.setCreaby(user.getEmail());
        trBookingLapangan.setCreadate(LocalDateTime.now());
        trBookingLapangan.setModiby(user.getEmail());
        trBookingLapangan.setModidate(LocalDateTime.now());
        trBookingLapangan.setStatus(1);
        tr_booking_lapangan_service.save(trBookingLapangan);

        return "redirect:/Konfirmasi-Ajak-Tanding";
    }

    @GetMapping("/Tolak-AjakTim")
    public String TolakAjakTim(@RequestParam("id") Integer id, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrAjakTanding trAjakTanding = ajakTandingService.getAllById(id);
        trAjakTanding.setId_status(4);
        ajakTandingService.save(trAjakTanding);

        return "redirect:/Konfirmasi-Ajak-Tanding";
    }

    @GetMapping("/AjakTim-Detail2")
    public String DetilAjakTim2(@RequestParam("id") Integer id, HttpSession session, Model model) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrAjakTanding ajakList = ajakTandingService.getAllById(id);
        model.addAttribute("listAjak", ajakList);

        DtAjakTanding dtAjakTanding = dtAjakTandingService.getAllByAjakTandings(id);
        model.addAttribute("listdetail", dtAjakTanding);

        List<MsTim> tim = timService.getAllTim();
        model.addAttribute("listTim", tim);

        return "page/detail_ajak_tanding2";
    }

    /////========================================Main Bareng=============================================//
    @GetMapping("/Play-Together")
    public String PlayTogether(HttpSession session, Model model) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        MsUser user = userService.getUserById(idus);

        List<TrJadwalLapangan> trJadwalLapangan = tr_jadwal_lapangan_service.getAllIdTim1AndTim2(user.getIdTim(), user.getIdTim());
//        List<TrJadwalLapangan> trJadwalLapangan = tr_jadwal_lapangan_service.getAll();
        model.addAttribute("listJadwal", trJadwalLapangan);

        List<MsLapangan> lapangans = lapanganService.getAllLapangan();
        model.addAttribute("listLapangan", lapangans);

        return "page/main_bareng";
    }

    @GetMapping("/Play-Together-On")
    public String PlayTogetherOn(@Param("id") int id, HttpSession session, Model model) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrJadwalLapangan trJadwalLapangan = tr_jadwal_lapangan_service.getById(id);
        trJadwalLapangan.setMainBareng(1);
        tr_jadwal_lapangan_service.save(trJadwalLapangan);

        return "redirect:/Play-Together";
    }

    @GetMapping("/Play-Together-Off")
    public String PlayTogetherOff(@Param("id") int id, HttpSession session, Model model) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrJadwalLapangan trJadwalLapangan = tr_jadwal_lapangan_service.getById(id);
        trJadwalLapangan.setMainBareng(0);
        tr_jadwal_lapangan_service.save(trJadwalLapangan);

        return "redirect:/Play-Together";
    }

    @GetMapping("/Play-Together-Detail")
    public String PlayTogetherDetail(@Param("id") int id,HttpSession session, Model model) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        List<TrMainBareng> mainBarengs = trMainbarengservice.getAllIdJadwalLapangan(id, 1);
        model.addAttribute("listJadwak", mainBarengs);

        List<MsUser> user = userService.getAllUser();
        model.addAttribute("listUser", user);

        List<MsStatus> statuses = statusService.getAllStatus();
        model.addAttribute("listStatus", statuses);

        return "page/main_bareng_detil";
    }

    @GetMapping("/Play-Together-Detail-Reject")
    public String PlayTogetherDetailReject(@Param("id") int id, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrMainBareng mainBareng = trMainbarengservice.getById(id);
        mainBareng.setIdStatus(4);
        mainBareng.setStatus(0);
        trMainbarengservice.save(mainBareng);

        return "redirect:/Play-Together-Detail?id="+mainBareng.getIdJadwalLapangan();
    }

    @GetMapping("/Play-Together-Detail-Confirm")
    public String PlayTogetherDetailConfirm(@Param("id") int id, HttpSession session) {
        int idus = -1;
        try {
            idus = (int) session.getAttribute("id_user");
        } catch (Exception e) {
            return "redirect:/page-login";
        }

        TrMainBareng mainBareng = trMainbarengservice.getById(id);
        mainBareng.setIdStatus(3);
        trMainbarengservice.save(mainBareng);

        return "redirect:/Play-Together-Detail?id="+mainBareng.getIdJadwalLapangan();
    }
}
