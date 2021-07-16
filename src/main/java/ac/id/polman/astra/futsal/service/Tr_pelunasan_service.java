package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.TrPelunasan;
import ac.id.polman.astra.futsal.repository.TrPelunasanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Tr_pelunasan_service {
    @Autowired
    TrPelunasanRepository trPelunasanRepository;

    public List<TrPelunasan> getAll(){
        List<TrPelunasan> a = (List<TrPelunasan>) trPelunasanRepository.findAll();
        return a;
    }
}
