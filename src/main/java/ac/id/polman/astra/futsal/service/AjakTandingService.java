package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsAkun;
import ac.id.polman.astra.futsal.model.MsTim;
import ac.id.polman.astra.futsal.model.TrAjakTanding;
import ac.id.polman.astra.futsal.model.TrDaftarTim;
import ac.id.polman.astra.futsal.repository.AjakTandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AjakTandingService {
    @Autowired
    AjakTandingRepository ajakTandingRepository;

    public List<TrAjakTanding> getAll(){
        List<TrAjakTanding> tandingList = (List<TrAjakTanding>) ajakTandingRepository.findAll();
        return tandingList;
    }

    public TrAjakTanding getAllById(int id){
        TrAjakTanding tandingList = ajakTandingRepository.findById(id);
        return tandingList;
    }

    public TrAjakTanding getLastId(){
        TrAjakTanding tandingList = ajakTandingRepository.findTopByOrderByIdDesc();
        return tandingList;
    }

    public TrAjakTanding getByIdandTanggalandJam(int id, Date tanggal, Time jam){
        TrAjakTanding tandingList = ajakTandingRepository.findByIdTim1AndTanggalAndJam(id, tanggal, jam);
        return tandingList;
    }

    public List<TrAjakTanding> getAllByTim(int id_tim){
        List<TrAjakTanding> tandingList = ajakTandingRepository.findByIdTim1(id_tim);
        return tandingList;
    }

    public List<TrAjakTanding> getAllByTim2(int id_tim){
        List<TrAjakTanding> tandingList = ajakTandingRepository.findByIdTim2(id_tim);
        return tandingList;
    }

    public void save(TrAjakTanding trAjakTanding){
        ajakTandingRepository.save(trAjakTanding);
    }
}
