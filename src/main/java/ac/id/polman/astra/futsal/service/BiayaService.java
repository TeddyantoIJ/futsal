package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsBiaya;
import ac.id.polman.astra.futsal.repository.BiayaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BiayaService {
    @Autowired
    BiayaRepository biayaRepository;

    public List<MsBiaya> getAllBiaya(){
        List<MsBiaya> msBiayaList = (List<MsBiaya>) biayaRepository.findAll();
        return msBiayaList;
    }

    public List<MsBiaya> findAllByStatus(int status){
        List<MsBiaya> biayalist = new ArrayList<>();
        biayalist = biayaRepository.findAllByStatus(status);
        return biayalist;
    }

    public MsBiaya getBiaya(int id_biaya){
        MsBiaya msBiaya = biayaRepository.findByIdBiaya(id_biaya);
        return msBiaya;
    }

    // ====================================
    public void saveBiaya(MsBiaya msBiaya){
        biayaRepository.save(msBiaya);
    }
}
