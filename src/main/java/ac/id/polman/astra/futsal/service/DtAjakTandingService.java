package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.DtAjakTanding;
import ac.id.polman.astra.futsal.model.TrPendaftaranMerchant;
import ac.id.polman.astra.futsal.repository.DtAjakTandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DtAjakTandingService {
    @Autowired
    DtAjakTandingRepository dtAjakTandingRepository;

    public List<DtAjakTanding> getAll(){
        List<DtAjakTanding> tandingList = (List<DtAjakTanding>) dtAjakTandingRepository.findAll();
        return tandingList;
    }

    public DtAjakTanding getAllById(int id){
        DtAjakTanding tandingList = dtAjakTandingRepository.findById(id);
        return tandingList;
    }

    public DtAjakTanding getAllByAjakTandings(int id_ajak){
        DtAjakTanding tandingList = dtAjakTandingRepository.findByIdAjakTanding(id_ajak);
        return tandingList;
    }

    public List<DtAjakTanding> getAllLast6(){
        List<DtAjakTanding> list = (List<DtAjakTanding>) dtAjakTandingRepository.findAll();
        List<DtAjakTanding> output = new ArrayList<>();
        int juju = (int) list.size() - 6;
        for (int i = list.size()-1 ; i >=juju  ; i--)
        {
            if(list.get(i).getStatus()== 1){
                output.add(list.get(i));
            }
        }

        return output;
    }

    public void save(DtAjakTanding dtAjakTanding) {
        dtAjakTandingRepository.save(dtAjakTanding);
    }
}
