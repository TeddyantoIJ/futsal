package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.TrMainBareng;
import ac.id.polman.astra.futsal.repository.MainBarengRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrMainBarengService {
    @Autowired
    MainBarengRepository mainBarengRepository;

    public List<TrMainBareng> getAll(){
        List<TrMainBareng> a = (List<TrMainBareng>) mainBarengRepository.findAll();
        return a;
    }

    public TrMainBareng getById(int id){
        TrMainBareng a = mainBarengRepository.findById(id);
        return a;
    }

    public List<TrMainBareng> getAllIdJadwalLapangan(int id, int status){
        List<TrMainBareng> a = (List<TrMainBareng>) mainBarengRepository.findByIdJadwalLapanganAndStatus(id, status);
        return a;
    }

    public void save(TrMainBareng a){
        mainBarengRepository.save(a);
    }

}
