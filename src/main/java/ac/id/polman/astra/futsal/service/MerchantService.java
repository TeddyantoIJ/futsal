package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsMerchant;
import ac.id.polman.astra.futsal.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {

    @Autowired
    MerchantRepository msMerchantRepository;

    public List<MsMerchant> getAllMerchant(){
        List<MsMerchant> msMerchantList = (List<MsMerchant>) msMerchantRepository.findAll();
        return msMerchantList;
    }

    public MsMerchant getMerchantById(int id_merchant){
        MsMerchant msMerchant = msMerchantRepository.findByIdMerchant(id_merchant);
        return msMerchant;
    }

    // ========================================================================

    public void saveMerchant(MsMerchant msMerchant){
//        msMerchantRepository.saveMerchant(
//                msMerchant.getNama(),
//                msMerchant.getAlamat(),
//                msMerchant.getTelephone(),
//                msMerchant.getNarahubung(),
//                msMerchant.getDeskripsi(),
//                msMerchant.getRating(),
//                msMerchant.getFoto(),
//                msMerchant.getBanner(),
//                msMerchant.getCreaby(),
//                msMerchant.getCreadate(),
//                msMerchant.getModiby(),
//                msMerchant.getModidate(),
//                msMerchant.getStatus(),
//                msMerchant.getId_user()
//        );
        msMerchantRepository.save(msMerchant);
    }

    public void update(MsMerchant msMerchant){
//        msMerchantRepository.updateMerchant(
//                msMerchant.getId_merchant(),
//                msMerchant.getNama(),
//                msMerchant.getAlamat(),
//                msMerchant.getTelephone(),
//                msMerchant.getNarahubung(),
//                msMerchant.getDeskripsi(),
//                msMerchant.getRating(),
//                msMerchant.getFoto(),
//                msMerchant.getBanner(),
//                msMerchant.getCreaby(),
//                msMerchant.getCreadate(),
//                msMerchant.getModiby(),
//                msMerchant.getModidate(),
//                msMerchant.getStatus(),
//                msMerchant.getId_user()
//        );
        msMerchantRepository.save(msMerchant);
    }
}
