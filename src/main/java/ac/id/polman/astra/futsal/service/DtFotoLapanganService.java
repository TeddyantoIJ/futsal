package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.DtFotolapangan;

import ac.id.polman.astra.futsal.repository.DtLapanganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DtFotoLapanganService {
    @Autowired
    DtLapanganRepository dtFotoLapanganRepository;

    public List<DtFotolapangan> getAllDtFotoLapanganByIdLapangan(int id_lapangan){
        List<DtFotolapangan> dtFotolapanganList = new ArrayList<>();
        dtFotolapanganList = dtFotoLapanganRepository.findAllByIdLapangan(id_lapangan);
        return dtFotolapanganList;
    }

    public List<DtFotolapangan> getAll(){
        List<DtFotolapangan> dtFotolapanganList = (List<DtFotolapangan>) dtFotoLapanganRepository.findAll();
        return dtFotolapanganList;
    }

    public DtFotolapangan getDtFotoLapanganById(int idFoto){
        DtFotolapangan dtFotolapangan = dtFotoLapanganRepository.findByIdFoto(idFoto);
        return dtFotolapangan;
    }
    // ====================================
    public void saveFotoLapangan(DtFotolapangan dtFotolapangan){
        dtFotoLapanganRepository.save(dtFotolapangan);
    }
    public void deleteFotoLapangan(DtFotolapangan dtFotolapangan){
        dtFotoLapanganRepository.delete(dtFotolapangan);
    }
}
