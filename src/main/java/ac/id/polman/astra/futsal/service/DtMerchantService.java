package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.DtMerchant;
import ac.id.polman.astra.futsal.repository.DtMerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DtMerchantService {
    @Autowired
    DtMerchantRepository dtMerchantRepository;

    public List<DtMerchant> getAllDtMerchant(){
        List<DtMerchant> dtMerchantList = (List<DtMerchant>) dtMerchantRepository.findAll();
        return dtMerchantList;
    }

    public List<DtMerchant> getAllDtMerchantByIdMerchant(int id){
        List<DtMerchant> dtMerchantList = dtMerchantRepository.findByIdMerchant(id);
        return dtMerchantList;
    }

    public DtMerchant getDtMerchantByIdDtMerchant(int id_dtmerchant){
        DtMerchant dtMerchant = dtMerchantRepository.findByIdDtmerchant(id_dtmerchant);
        return dtMerchant;
    }


    // =======================================================
    public void saveDtMerchant(DtMerchant dtMerchant){
        dtMerchantRepository.save(dtMerchant);
    }
}
