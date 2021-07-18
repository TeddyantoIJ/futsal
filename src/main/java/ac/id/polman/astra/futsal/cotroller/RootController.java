package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.*;
import ac.id.polman.astra.futsal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    Tr_pelunasan_service trPelunasanService;
    @Autowired
    AjakTandingService ajakTandingService;


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
        MsUser a = new MsUser();
        a.setTanggalLahir(new Date());
        model.addAttribute("user", a);
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
        return "page/merchant_order_booking";
    }

    @GetMapping("/my-merchant-processed-order")
    public String goto_my_merchant_processed_order(
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
        List<TrBookingLapangan> a = trBookinglapanganservice.getAllTerkonfirmasiByIdMerchant(idMerchant);
        List<MsLapangan> b = lapanganService.getAllLapanganByIdMerchant(idMerchant);
        List<MsTim> c = timService.getAllTim();

        model.addAttribute("booking", a);
        model.addAttribute("lapangan", b);
        model.addAttribute("tim",c);
        return "page/merchant_order_processed";
    }

    @GetMapping("/my-merchant-finished-order")
    public String goto_my_merchant_finished_order(
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
        List<TrBookingLapangan> a = trBookinglapanganservice.getAllFinishByIdMerchant(idMerchant);
        List<MsLapangan> b = lapanganService.getAllLapanganByIdMerchant(idMerchant);
        List<MsTim> c = timService.getAllTim();


        model.addAttribute("booking", a);
        model.addAttribute("lapangan", b);
        model.addAttribute("tim",c);
        return "page/merchant_order_finish";
    }

    @GetMapping("/my-merchant-invalid-order")
    public String goto_my_merchant_invalid_order(
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
        List<TrBookingLapangan> a = trBookinglapanganservice.getAllInvalidByIdMerchant(idMerchant);
        List<MsLapangan> b = lapanganService.getAllLapanganByIdMerchant(idMerchant);
        List<MsTim> c = timService.getAllTim();


        model.addAttribute("booking", a);
        model.addAttribute("lapangan", b);
        model.addAttribute("tim",c);
        return "page/merchant_order_invalid";
    }

    @GetMapping("/my-merchant-booking-payment")
    public String goto_my_merchant_booking_payment(
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
        List<TrBookingLapangan> a = trBookinglapanganservice.getAllPelunasanByIdMerchant(idMerchant);
        List<MsLapangan> b = lapanganService.getAllLapanganByIdMerchant(idMerchant);
        List<MsTim> c = timService.getAllTim();
        List<TrPelunasan> d = trPelunasanService.getAllByIdMerchant(idMerchant);


        model.addAttribute("booking", a);
        model.addAttribute("lapangan", b);
        model.addAttribute("tim",c);
        model.addAttribute("pelunasan", d);
        return "page/merchant_order_payment";
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
        MsMerchant msMerchant = merchantService.getMerchantById(msLapangan.getIdMerchant());
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
        a.setIdStatus(1);
        a.setIdTim(us.getIdTim());
        a.setNotifikasi(0);
        if(Integer.parseInt(waktu.split(":")[0]) > 16){
            a.setDp((int) (la.getHarga() * a.getDurasi1()) / 2);
            a.setSub_harga((int) (la.getHarga() * a.getDurasi1()));
        }else{
            a.setDp((int) (la.getHarga_penerangan() * a.getDurasi1()) / 2);
            a.setSub_harga((int) (la.getHarga_penerangan() * a.getDurasi1()));
        }
        a.setBukti_transfer("");
        a.setCreaby(us.getEmail());
        a.setCreadate(LocalDateTime.now());
        a.setModiby(us.getEmail());
        a.setModidate(LocalDateTime.now());
        a.setStatus(1);

        if(trBookinglapanganservice.save(a)){
            return "redirect:/";
        }else{
            return "redirect:/detail-lapangan/"+la.getIdLapangan();
        }
    }

    public String tanggal(Date dt) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateToString = df.format(dt);
        return dateToString;
    }

    public String jam(Date dt) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String dateToString = df.format(dt);
        return dateToString;
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

        TrAjakTanding trAjakTanding = ajakTandingService.getByIdandTanggalandJam(a.getIdTim(), a.getTanggal(), a.getJam());

        TrJadwalLapangan jadwal = new TrJadwalLapangan();
        jadwal.setIdTim1(a.getIdTim());
        if(trAjakTanding==null){
            jadwal.setId_tim2(a.getIdTim());
            jadwal.setMainBareng(0);
        }else{
            jadwal.setId_tim2(trAjakTanding.getIdTim2());
            jadwal.setMainBareng(1);
        }
        jadwal.setTanggal(a.getTanggal());
        jadwal.setJam(a.getJam());
        jadwal.setDurasi(a.getDurasi1());
        jadwal.setCreaby("System");
        jadwal.setCreadate(LocalDateTime.now());
        jadwal.setModiby("");
        jadwal.setModidate(LocalDateTime.now());
        jadwal.setIdLapangan(a.getId_lapangan());
        jadwal.setStatus(1);
        trJadwalLapanganService.save(jadwal);

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

    @GetMapping("/konfirmasi-booking-valid-payment/{id}")
    public String konfirmasi_booking_valid_payment(
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

        TrReview review = reviewService.getByIdBooking(a.getId());
        review.setStatus(1);
        reviewService.save(review);

        a.setModiby(us.getEmail());
        a.setModidate(LocalDateTime.now());
        trBookinglapanganservice.update_lunas(a);

        TrPelunasan pelunasan = trPelunasanService.getByIdBooking(a.getId());
        pelunasan.setStatus(1);
        trPelunasanService.save(pelunasan);

        TrJadwalLapangan jadwal = trJadwalLapanganService.getByJadwalJamLapangan(a);
        jadwal.setStatus(2);
        trJadwalLapanganService.save(jadwal);

        MsMerchant merchant = merchantService.getMerchantByIdUser(idUser);
        merchant.setRating(reviewService.getRatingByIdMerchant(merchant.getId_merchant()));
        merchant.setModiby("System");
        merchant.setModidate(LocalDateTime.now());
        merchantService.saveMerchant(merchant);
        return "redirect:/my-merchant-booking-payment";
    }

    @GetMapping("/konfirmasi-booking-invalid-payment/{id}")
    public String konfirmasi_booking_invalid_payment(
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

        TrReview review = reviewService.getByIdBooking(a.getId());
        reviewService.remove(review);

        a.setModiby(us.getEmail());
        a.setModidate(LocalDateTime.now());
        trBookinglapanganservice.back_to_konfirmasi(a);

        TrPelunasan pelunasan = trPelunasanService.getByIdBooking(a.getId());
        uploadController.hapus_bukti_tf(pelunasan.getBukti_bayar());
        trPelunasanService.save(pelunasan);

        TrJadwalLapangan jadwal = trJadwalLapanganService.getByJadwalJamLapangan(a);
        jadwal.setStatus(1);
        trJadwalLapanganService.save(jadwal);

        return "redirect:/my-merchant-booking-payment";
    }

    @GetMapping("/konfirmasi-booking-not-complete/{id}")
    public String konfirmasi_booking_not_complete(
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
        trBookinglapanganservice.update_tidak_selesai(a);

        TrJadwalLapangan j = trJadwalLapanganService.getByJadwalJamLapangan(a);
        j.setStatus(0);
        trJadwalLapanganService.save(j);
        return "redirect:/my-merchant-processed-order";
    }

    @GetMapping("/konfirmasi-booking-finish/{id}")
    public String konfirmasi_booking_finish(
            Model model,
            HttpSession session,
            @PathVariable int id
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
        trBookinglapanganservice.update_lunas(a);

        TrPelunasan pelunasan = new TrPelunasan();
        pelunasan.setCreaby(us.getEmail());
        pelunasan.setCreadate(LocalDateTime.now());
        pelunasan.setStatus(1);
        pelunasan.setModiby("");
        pelunasan.setModidate(LocalDateTime.now());
        pelunasan.setBukti_bayar("CASH");
        pelunasan.setIdTrbooking(a.getId());
        pelunasan.setNominal(a.getSub_harga() - a.getDp());
        trPelunasanService.save(pelunasan);

        TrReview review = new TrReview();
        review.setCreaby(us.getEmail());
        review.setCreadate(LocalDateTime.now());
        review.setStatus(0);
        review.setModiby("");
        review.setModidate(LocalDateTime.now());
        review.setReview("");
        review.setRating(0);
        review.setIdMerchant(lapanganService.getLapanganByIdLapangan(a.getId_lapangan()).getIdMerchant());
        review.setIdUser(us.getIdUser());
        review.setId_trbooking(trJadwalLapanganService.getByJadwalJamLapangan(a).getId());
        reviewService.save(review);

        TrJadwalLapangan jadwal = trJadwalLapanganService.getByJadwalJamLapangan(a);
        jadwal.setStatus(2);
        trJadwalLapanganService.save(jadwal);
        return "redirect:/my-merchant-processed-order";
    }

    @PostMapping("/konfirmasi-booking-finish")
    public String konfirmasi_booking_finish_post(
            Model model,
            HttpSession session,
            @RequestParam("id") int id,
            @RequestParam("bukti_transfer") MultipartFile file,
            TrReview rev
    ){
        int idUser = -1;
        try{
            idUser = (int) session.getAttribute("id_user");
        }catch (Exception e){
            return "redirect:/page-login";
        }
        MsUser us = userService.getUserById(idUser);
        TrJadwalLapangan j = trJadwalLapanganService.getById(id);
        TrBookingLapangan a = trBookinglapanganservice.getByIdLapanganJadwalAndTerkonfirmasi(j);
        a.setModiby(us.getEmail());
        a.setModidate(LocalDateTime.now());
        trBookinglapanganservice.update_pelunasan_diproses(a);

        TrPelunasan pelunasan = new TrPelunasan();
        pelunasan.setCreaby(us.getEmail());
        pelunasan.setCreadate(LocalDateTime.now());
        pelunasan.setStatus(-1);
        pelunasan.setModidate(LocalDateTime.now());
        pelunasan.setModiby("");
        pelunasan.setBukti_bayar(uploadController.upload_bukti_tf(file, "none"));
        pelunasan.setIdTrbooking(a.getId());
        pelunasan.setNominal(a.getSub_harga() - a.getDp());
        trPelunasanService.save(pelunasan);

        TrReview review = new TrReview();
        review.setCreaby(us.getEmail());
        review.setCreadate(LocalDateTime.now());
        review.setStatus(0);
        review.setModiby("");
        review.setModidate(LocalDateTime.now());
        review.setReview(rev.getReview());
        review.setRating(rev.getRating());
        review.setIdMerchant(lapanganService.getLapanganByIdLapangan(a.getId_lapangan()).getIdMerchant());
        review.setIdUser(us.getIdUser());
        review.setId_trbooking(id);
        reviewService.save(review);

        TrJadwalLapangan jadwal = trJadwalLapanganService.getByJadwalJamLapangan(a);
        jadwal.setStatus(-1);
        trJadwalLapanganService.save(jadwal);
        return "redirect:/my-tim-upcoming-schedule";
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

    @GetMapping("/my-tim-schedule-on-progress")
    public String goto_my_team_schedule_on_progress(
            Model model,
            HttpSession session
    ){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e) {
            return "redirect:/page-login";
        }

        int tim = userService.getUserById(id).getIdTim();
        List<TrBookingLapangan> a = trBookinglapanganservice.getAllDiprosesByIdTim(tim);
        List<MsLapangan> b = lapanganService.getAllLapangan();

        model.addAttribute("booking", a);
        model.addAttribute("lapangan", b);

        return "page/tim_schedule_on_progress";
    }

    @GetMapping("/my-tim-upcoming-schedule")
    public String goto_my_team_upcoming_schedule(
            Model model,
            HttpSession session
    ){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e) {
            return "redirect:/page-login";
        }

        int tim = userService.getUserById(id).getIdTim();
        List<TrJadwalLapangan> a = trJadwalLapanganService.getAllFutureByIdTim(tim);
        List<MsLapangan> b = lapanganService.getAllLapangan();
        List<MsTim> c = timService.getAllTim();

        model.addAttribute("jadwal", a);
        model.addAttribute("lapangan", b);
        model.addAttribute("tim", c);

        return "page/tim_schedule_upcoming";
    }

    @GetMapping("/my-tim-schedule-history")
    public String goto_my_tim_schedule_history(
            Model model,
            HttpSession session
    ){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e) {
            return "redirect:/page-login";
        }

        int tim = userService.getUserById(id).getIdTim();
        List<TrJadwalLapangan> a = trJadwalLapanganService.getAllPastByIdTim(tim);
        List<MsLapangan> b = lapanganService.getAllLapangan();
        List<MsTim> c = timService.getAllTim();
        List<TrReview> d = reviewService.getAll();

        model.addAttribute("jadwal", a);
        model.addAttribute("lapangan", b);
        model.addAttribute("tim", c);
        model.addAttribute("review", d);

        return "page/tim_schedule_history";
    }

    @PostMapping("/tim-review-merchant")
    public String tim_review_merchant(
            HttpSession session,
            TrReview review
    ){
        int id = -1;
        try{
            id = (int) session.getAttribute("id_user");
        }catch (Exception e) {
            return "redirect:/page-login";
        }
        TrReview news = reviewService.getById(review.getId());
        news.setReview(review.getReview());
        news.setRating(review.getRating());
        news.setStatus(1);
        news.setModiby(userService.getUserById(id).getEmail());
        news.setModidate(LocalDateTime.now());
        reviewService.save(news);

        MsMerchant merchant = merchantService.getMerchantByIdUser(news.getIdMerchant());
        merchant.setRating(reviewService.getRatingByIdMerchant(merchant.getId_merchant()));
        merchant.setModiby("System");
        merchant.setModidate(LocalDateTime.now());
        merchantService.saveMerchant(merchant);
        return "redirect:/my-tim-schedule-history";
    }
    // ======================================= TEAM ======================================

    @GetMapping("/team-show-all")
    public String goto_team_show_all(
            Model model,
            HttpSession session,
            @RequestParam("search") Optional<String> search
    ) {
        try {
            if ((boolean) session.getAttribute("login")) {

            }
        } catch (Exception e) {
            session.setAttribute("login", false);
        }

        List<MsTim> a = new ArrayList<>();
        if (!search.isPresent() || search.get().equals("")) {
            a = timService.getAllActive();
        } else {
            a = timService.getTeamByName(search.get());
        }

        model.addAttribute("timList", a);
        return "page/all_team";
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
        a.setIdStatus(2);
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
