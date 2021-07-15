package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import ac.id.polman.astra.futsal.model.TrJadwalLapangan;
import ac.id.polman.astra.futsal.repository.TrBookingLapanganRepository;
import ac.id.polman.astra.futsal.repository.TrJadwalLapanganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Tr_booking_lapangan_service {
    @Autowired
    TrBookingLapanganRepository trBookingLapanganRepository;
    @Autowired
    TrJadwalLapanganRepository trJadwalLapanganRepository;

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
            if(b.getId_status() != 1){
                c.remove(b);
            }
        }
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
                    System.out.println("JADWAL SAMA");
                    return false;
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_mulai){
                    System.out.println("JADWAL TABRAKAN 1");
                    return false;
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_mulai > jadwal_lama_mulai){
                    System.out.println("JADWAL TABRAKAN 2");
                    return false;
                }else if(jadwal_lama_mulai < jadwal_baru_mulai && jadwal_lama_selesai > jadwal_baru_selesai){
                    System.out.println("JADWAL TABRAKAN 3");
                    return false;
                }else if(jadwal_baru_mulai < jadwal_lama_mulai && jadwal_baru_selesai > jadwal_lama_selesai){
                    System.out.println("JADWAL TABRAKAN 4");
                    return false;
                }
            }
        }
        trBookingLapanganRepository.save(a);
        return true;
    }
}
