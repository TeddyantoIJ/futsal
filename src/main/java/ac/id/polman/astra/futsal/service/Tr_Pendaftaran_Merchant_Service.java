package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsMerchant;
import ac.id.polman.astra.futsal.model.TrPendaftaranMerchant;
import ac.id.polman.astra.futsal.repository.MerchantRepository;
import ac.id.polman.astra.futsal.repository.Tr_Pendaftaran_Merchant_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Tr_Pendaftaran_Merchant_Service {
    @Autowired
    Tr_Pendaftaran_Merchant_Repository trPendaftaranMerchantRepository;
    @Autowired
    MerchantRepository merchantRepository;

    public void save(TrPendaftaranMerchant data){
        trPendaftaranMerchantRepository.save(data);
    }

    public TrPendaftaranMerchant getByIdUser(int id_user){
        MsMerchant msMerchant = merchantRepository.findByIdUser(id_user);
        TrPendaftaranMerchant pendaftaranMerchant = new TrPendaftaranMerchant();
        try{
            pendaftaranMerchant = trPendaftaranMerchantRepository.findByIdMerchant(msMerchant.getId_merchant());
        }catch (Exception ex){

        }

        return pendaftaranMerchant;
    }

    public TrPendaftaranMerchant getPendaftaran(int id){
        return trPendaftaranMerchantRepository.findById(id);
    }

    public List<TrPendaftaranMerchant> getAll(){
        List<TrPendaftaranMerchant> list = (List<TrPendaftaranMerchant>) trPendaftaranMerchantRepository.findAll();
        List<TrPendaftaranMerchant> output = new ArrayList<>();

        for (int i = list.size()-1 ; i >= 0 ; i--)
        {
            output.add(list.get(i));
        }

        return output;
    }
}
