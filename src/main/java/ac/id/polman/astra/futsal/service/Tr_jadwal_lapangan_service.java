package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.TrJadwalLapangan;
import ac.id.polman.astra.futsal.model.TrPendaftaranMerchant;
import ac.id.polman.astra.futsal.repository.TrJadwalLapanganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Tr_jadwal_lapangan_service {
    @Autowired
    TrJadwalLapanganRepository trJadwalLapanganRepository;

    public List<TrJadwalLapangan> getAll(){
        List<TrJadwalLapangan> a = (List<TrJadwalLapangan>) trJadwalLapanganRepository.findAll();
        return a;
    }

    public List<TrJadwalLapangan> getAllByIdLapangan(int id){
        List<TrJadwalLapangan> a = trJadwalLapanganRepository.findAllByIdLapanganOrderByTanggalAscJamAsc(id);
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
}
