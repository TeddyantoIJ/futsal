package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.TrDaftarTim;
import ac.id.polman.astra.futsal.repository.Tr_daftar_tim_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Tr_daftar_tim_service {
    @Autowired
    Tr_daftar_tim_repository daftarTimRepository;

    public List<TrDaftarTim> getAllIdTim(int id_tim){
        List<TrDaftarTim> timList = new ArrayList<>();
        timList = daftarTimRepository.findAllByIdTim(id_tim);
        return timList;
    }

    public TrDaftarTim getTimById(int id){
        TrDaftarTim daftartim = daftarTimRepository.findAllById(id);
        return daftartim;
    }

    public List<TrDaftarTim> getAllByIdUserAndIdTim(int idUser, int idTim){
        List<TrDaftarTim> daftarTims = new ArrayList<>();
        List<TrDaftarTim> daftars = daftarTimRepository.findAllByIdUserAndIdTim(idUser, idTim);
        for(TrDaftarTim daf : daftars){
            daftarTims.add(daf);
        }
        return daftarTims;
    }

//    =================================
    public boolean save(TrDaftarTim tim){
        List<TrDaftarTim> pendaftaran = getAllByIdUserAndIdTim(tim.getIdUser(), tim.getIdTim());
        for(TrDaftarTim daf : pendaftaran){
            if(daf.getIdStatus() == 2){
                return false;
            }
        }
        daftarTimRepository.save(tim);
        return true;
    }
}
