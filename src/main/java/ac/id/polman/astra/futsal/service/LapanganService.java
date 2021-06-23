package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.repository.LapanganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LapanganService {
    @Autowired
    LapanganRepository lapanganRepository;

    public List<MsLapangan> getAllLapangan(){
        List<MsLapangan> msLapanganList = (List<MsLapangan>) lapanganRepository.findAll();
        return msLapanganList;
    }

    public List<MsLapangan> getAllLapanganByIdMerchant(int id_merchant){
        List<MsLapangan> msLapanganList = lapanganRepository.findAllByIdMerchant(id_merchant);
        return msLapanganList;
    }

    public MsLapangan getLapanganByIdLapangan(int id_lapangan){
        MsLapangan msLapangan = lapanganRepository.findByIdLapangan(id_lapangan);
        return msLapangan;
    }
    //===================================
    public void saveLapangan(MsLapangan msLapangan){
        lapanganRepository.save(msLapangan);
    }

}
