package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsStatus;
import ac.id.polman.astra.futsal.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;

    public List<MsStatus> getAllStatus(){
        List<MsStatus> msStatusList = (List<MsStatus>) statusRepository.findAll();
        return msStatusList;
    }

    public MsStatus getStatus(int id_status){
        MsStatus msStatus = statusRepository.findByIdStatus(id_status);
        return msStatus;
    }

    // ====================================
    public void saveStatus(MsStatus msStatus){
        statusRepository.save(msStatus);
    }
}
