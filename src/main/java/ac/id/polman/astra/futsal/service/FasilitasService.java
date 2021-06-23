package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsFasilitas;
import ac.id.polman.astra.futsal.repository.FasilitasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FasilitasService {

    @Autowired
    FasilitasRepository fasilitasRepository;

    public List<MsFasilitas> getAllFacilities(){
        List<MsFasilitas> msFasilitasList = (List<MsFasilitas>) fasilitasRepository.findAll();
        return msFasilitasList;
    }

    public MsFasilitas getFacilities(int id_fasilitas){
        MsFasilitas msFasilitas = fasilitasRepository.findByIdFasilitas(id_fasilitas);
        return msFasilitas;
    }

    // ======================================

    public void savefasilitas(MsFasilitas msFasilitas){
        fasilitasRepository.save(msFasilitas);
    }
}
