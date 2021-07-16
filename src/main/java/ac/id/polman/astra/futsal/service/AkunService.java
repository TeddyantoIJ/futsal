package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsAkun;
import ac.id.polman.astra.futsal.repository.AkunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AkunService {
    @Autowired
    AkunRepository akunRepository;

    public List<MsAkun> getAllAkun(){
        List<MsAkun> msAkunList = (List<MsAkun>) akunRepository.findAll();
        return msAkunList;
    }

    public List<MsAkun> getAllAkunByIdRole(int id_role){
        List<MsAkun> msAkunList = akunRepository.findAllByIdRole(id_role);
        return msAkunList;
    }

    public MsAkun getAkunByIdAkun(int id_akun){
        MsAkun msAkun = akunRepository.findByIdAkun(id_akun);
        return msAkun;
    }

    public MsAkun getUserByUsername(String username){
        MsAkun msAkun = akunRepository.findByUsername(username);
        return msAkun;
    }

    public void update(MsAkun msAkun){
        akunRepository.save(msAkun);
    }

    //===================================
    public Boolean saveAkun(MsAkun msAkun){
        MsAkun a = getUserByUsername(msAkun.getUsername());
        if(a == null){
            akunRepository.save(msAkun);
            return  true;
        }
        return false;
    }
}
