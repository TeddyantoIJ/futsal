package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import ac.id.polman.astra.futsal.model.TrJadwalLapangan;
import ac.id.polman.astra.futsal.model.TrPelunasan;
import ac.id.polman.astra.futsal.repository.LapanganRepository;
import ac.id.polman.astra.futsal.repository.TrBookingLapanganRepository;
import ac.id.polman.astra.futsal.repository.TrJadwalLapanganRepository;
import ac.id.polman.astra.futsal.repository.TrPelunasanRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Tr_booking_lapangan_service {
    @Autowired
    TrBookingLapanganRepository trBookingLapanganRepository;
    @Autowired
    TrJadwalLapanganRepository trJadwalLapanganRepository;
    @Autowired
    LapanganRepository lapanganRepository;
    @Autowired
    TrPelunasanRepository trPelunasanRepository;

    public List<TrBookingLapangan> getAll(){
        List<TrBookingLapangan> a = trBookingLapanganRepository.findAllByOrderByTanggalAscJamAsc();
        return a;
    }

    public List<TrBookingLapangan> getAllActiveByITim(int id){
        List<TrBookingLapangan> a = getAll();
        List<TrBookingLapangan> c = new ArrayList<>();
        for (TrBookingLapangan b: a) {
            if(b.getStatus() == 1 && b.getIdTim() == id){
                c.add(b);
            }
        }
        return c;
    }

    public List<TrBookingLapangan> getAllMenungguPembayaranByIdTim(int id){
        List<TrBookingLapangan> a = getAllActiveByITim(id);
        List<TrBookingLapangan> c = getAllActiveByITim(id);
        for(TrBookingLapangan b : a){
            if(b.getIdStatus() != 1){
                c.remove(b);
            }
        }
        return c;
    }

    public TrBookingLapangan getById(int id){
        List<TrBookingLapangan> a = getAll();
        for(TrBookingLapangan b : a){
            if(b.getId() == id){
                return b;
            }
        }
        return null;
    }

    public TrBookingLapangan getByIdLapanganJadwalAndTerkonfirmasi(TrJadwalLapangan a){
        List<TrBookingLapangan> booking = getAllByIdLapangan(a.getIdLapangan());
        for(TrBookingLapangan book : booking){
            if(book.getIdStatus() == 3){
                if(book.getTanggal().compareTo(a.getTanggal()) == 0 && book.getJam().compareTo(a.getJam()) == 0){
                    return book;
                }
            }
        }
        return null;
    }

    public List<TrBookingLapangan> getAllByIdLapangan(int id){
        List<TrBookingLapangan> a = getAll();
        List<TrBookingLapangan> c = new ArrayList<>();
        for (TrBookingLapangan b : a) {
            if(b.getId_lapangan() == id){
                c.add(b);
            }
        }
        return c;
    }

    public List<TrBookingLapangan> getAllDiprosesByIdMerchant(int id){
        List<MsLapangan> lapangan = lapanganRepository.findAllByIdMerchant(id);
        List<TrBookingLapangan> a = getAll();
        List<TrBookingLapangan> c = new ArrayList<>();
        for(TrBookingLapangan b : a){
            if(b.getIdStatus() == 2){
                for(MsLapangan d : lapangan){
                    if(d.getIdLapangan() == b.getId_lapangan()){
                        c.add(b);
                    }
                }
            }
        }
        System.out.println(c.size());
        return c;
    }

    public List<TrBookingLapangan> getAllTerkonfirmasiByIdMerchant(int id){
        List<MsLapangan> lapangan = lapanganRepository.findAllByIdMerchant(id);
        List<TrBookingLapangan> a = getAll();
        List<TrBookingLapangan> c = new ArrayList<>();
        for(TrBookingLapangan b : a){
            if(b.getIdStatus() == 3){
                for(MsLapangan d : lapangan){
                    if(d.getIdLapangan() == b.getId_lapangan()){
                        c.add(b);
                    }
                }
            }
        }
        System.out.println(c.size());
        return c;
    }

    public List<TrBookingLapangan> getAllInvalidByIdMerchant(int id){
        List<MsLapangan> lapangan = lapanganRepository.findAllByIdMerchant(id);
        List<TrBookingLapangan> a = getAll();
        List<TrBookingLapangan> c = new ArrayList<>();
        for(TrBookingLapangan b : a){
            if(b.getIdStatus() == 6){
                for(MsLapangan d : lapangan){
                    if(d.getIdLapangan() == b.getId_lapangan()){
                        c.add(b);
                    }
                }
            }
        }
        System.out.println(c.size());
        return c;
    }

    public List<TrBookingLapangan> getAllFinishByIdMerchant(int id){
        List<MsLapangan> lapangan = lapanganRepository.findAllByIdMerchant(id);

        List<TrBookingLapangan> a = getAll();
        List<TrBookingLapangan> c = new ArrayList<>();
        for(TrBookingLapangan b : a){
            if(b.getIdStatus() == 5){
                for(MsLapangan d : lapangan){
                    if(d.getIdLapangan() == b.getId_lapangan()){
                        c.add(b);
                    }
                }
            }
        }
        System.out.println(c.size());
        return c;
    }

    public List<TrBookingLapangan> getAllPelunasanByIdMerchant(int id){
        List<MsLapangan> lapangan = lapanganRepository.findAllByIdMerchant(id);

        List<TrBookingLapangan> a = getAll();
        List<TrBookingLapangan> c = new ArrayList<>();
        for(TrBookingLapangan b : a){
            if(b.getIdStatus() == 8){
                for(MsLapangan d : lapangan){
                    if(d.getIdLapangan() == b.getId_lapangan()){
                        c.add(b);
                    }
                }
            }
        }
        System.out.println(c.size());
        return c;
    }

    public List<TrBookingLapangan> getAllDiprosesByIdTim(int id){
        List<TrBookingLapangan> a = getAll();
        List<TrBookingLapangan> c = new ArrayList<>();
        for(TrBookingLapangan b : a){
            if(b.getIdStatus() == 2 && b.getIdTim() == id){
                c.add(b);
            }
        }
        System.out.println(c.size());
        return c;
    }

    public List<TrBookingLapangan> getAllIncomeByIdMerchant(int id, String tanggal1, String tanggal2){
        List<MsLapangan> lapangan = lapanganRepository.findAllByIdMerchant(id);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<TrBookingLapangan> a = getAll();
        List<TrBookingLapangan> c = new ArrayList<>();

        TrBookingLapangan total = new TrBookingLapangan();
        int total_uang = 0;
        for(TrBookingLapangan b : a){
            try{
                Date modidate = sdf.parse(b.getModidate().toString());
                Date comp1 = sdf.parse(tanggal1);
                Date comp2 = sdf.parse(tanggal2);
                if(modidate.compareTo(comp1) >= 0 && modidate.compareTo(comp2) <= 0){
                    if(b.getIdStatus() == 5 || b.getIdStatus() == 3){
                        for(MsLapangan d : lapangan){
                            if(d.getIdLapangan() == b.getId_lapangan()){
                                c.add(b);
                                if(b.getIdStatus() == 3){
                                    total_uang += b.getDp();
                                }else{
                                    total_uang += b.getSub_harga();
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
                System.out.println("Error pak");
            }
        }
        total.setSub_harga(total_uang);
        total.setIdStatus(-1);
        c.add(total);
        return c;
    }


//    ====================================
    public boolean save(TrBookingLapangan a){
        List<TrJadwalLapangan> tj = new ArrayList<>();
        SimpleDateFormat fm = new SimpleDateFormat("HH:mm");
        tj = trJadwalLapanganRepository.findAllByIdLapanganOrderByTanggalAscJamAsc(a.getId_lapangan());
        for (TrJadwalLapangan t : tj ) {
            if(t.getTanggal().compareTo(a.getTanggal()) == 0){
                int jadwal_lama_mulai = Integer.parseInt(t.getJam().toString().split(":")[0]);
                int jadwal_lama_selesai = jadwal_lama_mulai + t.getDurasi1();
                int jadwal_baru_mulai = Integer.parseInt(a.getJam().toString().split(":")[0]);
                int jadwal_baru_selesai = jadwal_baru_mulai + a.getDurasi1();

                if(jadwal_lama_mulai == jadwal_baru_mulai){
                    System.out.println("JADWAL LAPANGAN SAMA DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_mulai){
                    System.out.println("JADWAL LAPANGAN TABRAKAN 1 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_mulai > jadwal_lama_mulai){
                    System.out.println("JADWAL LAPANGAN TABRAKAN 2 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_selesai){
                    System.out.println("JADWAL LAPANGAN TABRAKAN 3 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_selesai > jadwal_lama_selesai){
                    System.out.println("JADWAL LAPANGAN TABRAKAN 4 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }
            }
        }

        trBookingLapanganRepository.save(a);
        return true;
    }

    // VALIDASI TIDAK BISA BAYAR BOOKING JIKA SEDANG ADA PROSES LAIN
    public boolean bayar(TrBookingLapangan a){
        List<TrJadwalLapangan> tj = new ArrayList<>();
        SimpleDateFormat fm = new SimpleDateFormat("HH:mm");
        tj = trJadwalLapanganRepository.findAllByIdLapanganOrderByTanggalAscJamAsc(a.getId_lapangan());
        for (TrJadwalLapangan t : tj ) {
            if(t.getTanggal().compareTo(a.getTanggal()) == 0){
                int jadwal_lama_mulai = Integer.parseInt(t.getJam().toString().split(":")[0]);
                int jadwal_lama_selesai = jadwal_lama_mulai + t.getDurasi1();
                int jadwal_baru_mulai = Integer.parseInt(a.getJam().toString().split(":")[0]);
                int jadwal_baru_selesai = jadwal_baru_mulai + a.getDurasi1();

                if(jadwal_lama_mulai == jadwal_baru_mulai){
                    System.out.println("JADWAL LAPANGAN SAMA DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_mulai){
                    System.out.println("JADWAL LAPANGAN TABRAKAN 1 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_mulai > jadwal_lama_mulai){
                    System.out.println("JADWAL LAPANGAN TABRAKAN 2 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_selesai){
                    System.out.println("JADWAL LAPANGAN TABRAKAN 3 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_selesai > jadwal_lama_selesai){
                    System.out.println("JADWAL LAPANGAN TABRAKAN 4 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }
            }
        }

        List<TrBookingLapangan> bk = getAllByIdLapangan(a.getId_lapangan());
        for (TrBookingLapangan t : bk ) {
            if(t.getTanggal().compareTo(a.getTanggal()) == 0 && t.getIdStatus() == 2){
                int jadwal_lama_mulai = Integer.parseInt(t.getJam().toString().split(":")[0]);
                int jadwal_lama_selesai = jadwal_lama_mulai + t.getDurasi1();
                int jadwal_baru_mulai = Integer.parseInt(a.getJam().toString().split(":")[0]);
                int jadwal_baru_selesai = jadwal_baru_mulai + a.getDurasi1();

                if(jadwal_lama_mulai == jadwal_baru_mulai){
                    System.out.println("JADWAL SEDANG ADA PROSES SAMA DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_mulai){
                    System.out.println("JADWAL SEDANG ADA PROSES TABRAKAN 1 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_mulai > jadwal_lama_mulai){
                    System.out.println("JADWAL SEDANG ADA PROSES TABRAKAN 2 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_selesai){
                    System.out.println("JADWAL SEDANG ADA PROSES TABRAKAN 3 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_selesai > jadwal_lama_selesai){
                    System.out.println("JADWAL SEDANG ADA PROSES TABRAKAN 4 DENGAN JADWAL ID : " + t.getId());
                    return false;
                }
            }
        }
        trBookingLapanganRepository.save(a);
        return true;
    }

    public boolean update(TrBookingLapangan a){
        List<TrBookingLapangan> tj = new ArrayList<>();
        SimpleDateFormat fm = new SimpleDateFormat("HH:mm");
        tj = getAllByIdLapangan(a.getId_lapangan());
        for (TrBookingLapangan t : tj ) {
            if(t.getTanggal().compareTo(a.getTanggal()) == 0){
                int jadwal_lama_mulai = Integer.parseInt(t.getJam().toString().split(":")[0]);
                int jadwal_lama_selesai = jadwal_lama_mulai + t.getDurasi1();
                int jadwal_baru_mulai = Integer.parseInt(a.getJam().toString().split(":")[0]);
                int jadwal_baru_selesai = jadwal_baru_mulai + a.getDurasi1();

                if(jadwal_lama_mulai == jadwal_baru_mulai){
                    System.out.println("JADWAL SAMA DENGAN JADWAL ID : " + t.getId());
                    update_gagal(t);
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_mulai){
                    System.out.println("JADWAL TABRAKAN 1 DENGAN JADWAL ID : " + t.getId());
                    update_gagal(t);
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_mulai > jadwal_lama_mulai){
                    System.out.println("JADWAL TABRAKAN 2 DENGAN JADWAL ID : " + t.getId());
                    update_gagal(t);
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_selesai){
                    System.out.println("JADWAL TABRAKAN 3 DENGAN JADWAL ID : " + t.getId());
                    update_gagal(t);
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_selesai > jadwal_lama_selesai){
                    System.out.println("JADWAL TABRAKAN 4 DENGAN JADWAL ID : " + t.getId());
                    update_gagal(t);
                }
            }
        }
        trBookingLapanganRepository.save(a);
        return true;
    }

    public void update_gagal(TrBookingLapangan a){
        a.setIdStatus(6);
        trBookingLapanganRepository.save(a);
    }

    public void update_batal(TrBookingLapangan a){
        a.setStatus(0);
        trBookingLapanganRepository.save(a);
    }

    public void update_tidak_selesai(TrBookingLapangan a){
        a.setIdStatus(7);
        trBookingLapanganRepository.save(a);
    }

    public void update_terkonfirmasi(TrBookingLapangan a){
        a.setIdStatus(3);

        List<TrBookingLapangan> tj = new ArrayList<>();
        SimpleDateFormat fm = new SimpleDateFormat("HH:mm");
        tj = getAllByIdLapangan(a.getId_lapangan());
        for (TrBookingLapangan t : tj ) {
            if(t.getTanggal().compareTo(a.getTanggal()) == 0 && t.getIdStatus() == 2){
                int jadwal_lama_mulai = Integer.parseInt(t.getJam().toString().split(":")[0]);
                int jadwal_lama_selesai = jadwal_lama_mulai + t.getDurasi1();
                int jadwal_baru_mulai = Integer.parseInt(a.getJam().toString().split(":")[0]);
                int jadwal_baru_selesai = jadwal_baru_mulai + a.getDurasi1();

                if(jadwal_lama_mulai == jadwal_baru_mulai){
                    System.out.println("update_terkonfirmasi JADWAL SAMA DENGAN JADWAL ID : " + t.getId());
                    update_gagal(t);
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_mulai){
                    System.out.println("update_terkonfirmasi JADWAL TABRAKAN 1 DENGAN JADWAL ID : " + t.getId());
                    update_gagal(t);
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_mulai > jadwal_lama_mulai){
                    System.out.println("update_terkonfirmasi JADWAL TABRAKAN 2 DENGAN JADWAL ID : " + t.getId());
                    update_gagal(t);
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_selesai){
                    System.out.println("update_terkonfirmasi JADWAL TABRAKAN 3 DENGAN JADWAL ID : " + t.getId());
                    update_gagal(t);
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_selesai > jadwal_lama_selesai){
                    System.out.println("update_terkonfirmasi JADWAL TABRAKAN 4 DENGAN JADWAL ID : " + t.getId());
                    update_gagal(t);
                }
            }
        }
        trBookingLapanganRepository.save(a);
    }

    public void update_transaksi_selesai(TrBookingLapangan a){
        a.setIdStatus(9);
        trBookingLapanganRepository.save(a);
    }

    public void update_lunas(TrBookingLapangan a){
        a.setIdStatus(5);
        trBookingLapanganRepository.save(a);
    }

    public void update_pelunasan_diproses(TrBookingLapangan a){
        a.setIdStatus(8);
        trBookingLapanganRepository.save(a);
    }

    public void back_to_konfirmasi(TrBookingLapangan a){
        a.setIdStatus(5);
        trBookingLapanganRepository.save(a);
    }
}
