package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.TrDaftarTim;
import ac.id.polman.astra.futsal.repository.DaftarTimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DaftarTimService {
    @Autowired
    DaftarTimRepository daftarTimRepository;

    public List<TrDaftarTim> getAllIdTim(int id_tim){
        List<TrDaftarTim> timList = new ArrayList<>();
        timList = daftarTimRepository.findAllByIdTim(id_tim);
        return timList;
    }

    public TrDaftarTim getTimById(int id){
        TrDaftarTim daftartim = daftarTimRepository.findAllById(id);
        return daftartim;
    }
    // =======================================================
    public void saveTrDaftarTim(TrDaftarTim daftarTim){
        daftarTimRepository.save(daftarTim);
    }
}
