package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import ac.id.polman.astra.futsal.model.TrJadwalLapangan;
import ac.id.polman.astra.futsal.model.TrPelunasan;
import ac.id.polman.astra.futsal.model.TrPendaftaranMerchant;
import ac.id.polman.astra.futsal.repository.TrBookingLapanganRepository;
import ac.id.polman.astra.futsal.repository.TrJadwalLapanganRepository;
import ac.id.polman.astra.futsal.repository.TrPelunasanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Tr_jadwal_lapangan_service {
    @Autowired
    TrJadwalLapanganRepository trJadwalLapanganRepository;
    @Autowired
    TrBookingLapanganRepository trBookingLapanganRepository;
    @Autowired
    TrPelunasanRepository trPelunasanRepository;

    public List<TrJadwalLapangan> getAll(){
        List<TrJadwalLapangan> a = (List<TrJadwalLapangan>) trJadwalLapanganRepository.findAll();
        return a;
    }

    public List<TrJadwalLapangan> getAllFutureAscending(){
        List<TrJadwalLapangan> a = new ArrayList<>();
        List<TrJadwalLapangan> booking = trJadwalLapanganRepository.findAllByOrderByTanggalAscJamAsc();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            Date comp = sdf.parse(sdf.format(new Date()));
            for(TrJadwalLapangan lapangan : booking){
                if(lapangan.getTanggal().compareTo(comp) > 0){
                    a.add(lapangan);
                }else if(lapangan.getTanggal().compareTo(comp) == 0 && lapangan.getJam().compareTo(comp) >= 0){
                    a.add(lapangan);
                }
            }
        }catch (Exception e) {
            System.out.println("Masuk error");
        }
        return a;
    }

    public List<TrJadwalLapangan> get6AscendingPractice(){
        List<TrJadwalLapangan> a = new ArrayList<>();
        int limit = 0;
        for(TrJadwalLapangan jadwal : getAllAscendingPractice()){
            a.add(jadwal);
            limit++;
            if(limit == 4){
                break;
            }
        }
        return a;
    }
    public List<TrJadwalLapangan> get6AscendingFriendly(){
        List<TrJadwalLapangan> a = new ArrayList<>();
        int limit = 0;
        for(TrJadwalLapangan jadwal : getAllAscendingFriendly()){
            a.add(jadwal);
            limit++;
            if(limit == 4){
                break;
            }
        }
        return a;
    }

    public List<TrJadwalLapangan> getAscendingPractice(){
        List<TrJadwalLapangan> a = new ArrayList<>();
        int limit = 0;
        for(TrJadwalLapangan jadwal : getAllAscendingPractice()){
            a.add(jadwal);
            limit++;
        }
        return a;
    }
    public List<TrJadwalLapangan> getAscendingFriendly(){
        List<TrJadwalLapangan> a = new ArrayList<>();
        int limit = 0;
        for(TrJadwalLapangan jadwal : getAllAscendingFriendly()){
            a.add(jadwal);
            limit++;
        }
        return a;
    }

    public List<TrJadwalLapangan> getAllAscendingPractice(){
        List<TrJadwalLapangan> a = new ArrayList<>();
        for(TrJadwalLapangan jadwal : getAllFutureAscending()){
            if(jadwal.getIdTim1() == jadwal.getIdTim2()){
                a.add(jadwal);
            }
        }
        return a;
    }
    public List<TrJadwalLapangan> getAllAscendingFriendly(){
        List<TrJadwalLapangan> a = new ArrayList<>();
        for(TrJadwalLapangan jadwal : getAllFutureAscending()){
            if(jadwal.getIdTim1() != jadwal.getIdTim2()){
                a.add(jadwal);
            }
        }
        return a;
    }

    public TrJadwalLapangan getById(int id){
        TrJadwalLapangan a = trJadwalLapanganRepository.findById(id);
        return a;
    }

    public List<TrJadwalLapangan> getAllByIdLapangan(int id){
        List<TrJadwalLapangan> a = trJadwalLapanganRepository.findAllByIdLapanganOrderByTanggalAscJamAsc(id);
        return a;
    }

    public List<TrJadwalLapangan> getAllIdTim1AndTim2(int id, int id2){
        List<TrJadwalLapangan> a = trJadwalLapanganRepository.findByIdTim1AndIdTim2(id, id2);
        return a;
    }

    public List<TrJadwalLapangan> getAllByIdAndByDate(int id, String tanggal){
        List<TrJadwalLapangan> a = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(tanggal);
        try{
            Date comp = sdf.parse(tanggal);
            System.out.printf(comp.toString());
            for(TrJadwalLapangan b : trJadwalLapanganRepository.findAllByIdLapanganOrderByTanggalAscJamAsc(id)){
                if(b.getTanggal().compareTo(comp) >= 0){
                    a.add(b);
                }
                System.out.println(b.getTanggal());
            }
        }catch (Exception e){

        }
        return a;
    }

    public List<TrJadwalLapangan> getAllNextByIdLapangan(int id){
        List<TrJadwalLapangan> a = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date comp = sdf.parse(sdf.format(new Date()));
            for(TrJadwalLapangan b : trJadwalLapanganRepository.findAllByIdLapanganOrderByTanggalAscJamAsc(id)){
                if(b.getTanggal().compareTo(comp) >= 0){
                    a.add(b);
                }
            }
        }catch (Exception e){

        }
        return a;
    }

    public List<TrJadwalLapangan> getAllFutureByIdTim(int idTim1){
        List<TrJadwalLapangan> a = new ArrayList<>();
        List<TrBookingLapangan> booking = trBookingLapanganRepository.findAllByIdTimOrderByTanggalAscJamAsc(idTim1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date comp = sdf.parse(sdf.format(new Date()));
            for(TrJadwalLapangan b : getAllByIdTim(idTim1)){
                for(TrBookingLapangan book : booking){
                    if(book.getJam().compareTo(b.getJam()) == 0 && book.getTanggal().compareTo(b.getTanggal()) == 0){
                        if(b.getTanggal().compareTo(comp) >= 0){
                            a.add(b);
                        }
                    }
                }


            }
        }catch (Exception e) {
            System.out.println("Masuk error");
        }
        return a;
    }
    public List<TrJadwalLapangan> getAllByIdTim(int idTim){
        List<TrJadwalLapangan> a = trJadwalLapanganRepository.findAllByIdTim1OrderByTanggalAscJamAsc(idTim);
        return a;
    }
    public List<TrJadwalLapangan> getAllPastByIdTim(int idTim1){
        List<TrJadwalLapangan> a = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date comp = sdf.parse(sdf.format(new Date()));
            for(TrJadwalLapangan b : getAllByIdTim(idTim1)){
                if(b.getTanggal().compareTo(comp) < 0){
                    a.add(b);
                }
            }
        }catch (Exception e){

        }
        return a;
    }

    public TrJadwalLapangan getByJadwalJamLapangan(TrBookingLapangan booking){
        List<TrJadwalLapangan> a = getAllByIdTim(booking.getIdTim());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        for(TrJadwalLapangan jadwal : a){
            try{
                Date tanggal = sdf.parse(sdf.format(jadwal.getTanggal()));
                if(tanggal.compareTo(booking.getTanggal()) == 0){
                    Date jam = parser.parse(parser.format(booking.getJam()));
                    Date jamLapangan = parser.parse(parser.format(jadwal.getJam()));
                    if(jam.compareTo(jamLapangan) == 0){
                        return jadwal;
                    }
                }
            }catch (Exception e){

            }
        }
        return new TrJadwalLapangan();
    }

//    ================================\\
    public void save(TrJadwalLapangan a){
        trJadwalLapanganRepository.save(a);
    }

}
