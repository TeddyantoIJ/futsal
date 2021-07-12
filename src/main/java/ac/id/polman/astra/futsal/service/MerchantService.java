package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsMerchant;
import ac.id.polman.astra.futsal.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class MerchantService {

    @Autowired
    MerchantRepository msMerchantRepository;

    public List<MsMerchant> getAllMerchant(){
        List<MsMerchant> msMerchantList = (List<MsMerchant>) msMerchantRepository.findAll();
        return msMerchantList;
    }
    public List<MsMerchant> get6MerchantActive(){
        List<MsMerchant> msMerchantList = (List<MsMerchant>) msMerchantRepository.findAll();
        List<MsMerchant> output = new ArrayList<>();
        int i = 0;
        for (MsMerchant a : msMerchantList)
        {
            if(a.getStatus() == 1){
                output.add(a);
                i++;
            }
            if(i == 6){
                break;
            }
        }
        return output;
    }
    public List<MsMerchant> getAllActive(){
        List<MsMerchant> msMerchantList = (List<MsMerchant>) msMerchantRepository.findAll();
        List<MsMerchant> output = new ArrayList<>();
        for (MsMerchant a : msMerchantList)
        {
            if(a.getStatus() == 1){
                output.add(a);
            }
        }
        return output;
    }
    public List<MsMerchant> getMerchantByName(String cari){
        List<MsMerchant> a = getAllActive();
        List<MsMerchant> output = new ArrayList<>();
        for (MsMerchant b : a)
        {
            if(b.getNama().toLowerCase().contains(cari.toLowerCase())
                    || b.getAlamat().toLowerCase().contains(cari.toLowerCase())
                    || b.getDeskripsi().toLowerCase().contains(cari.toLowerCase())
                    || b.getNarahubung().toLowerCase().contains(cari.toLowerCase())){
                output.add(b);
            }
        }
        return output;
    }
    public MsMerchant getMerchantById(int id_merchant){
        MsMerchant msMerchant = msMerchantRepository.findByIdMerchant(id_merchant);
        return msMerchant;
    }
    public MsMerchant getMerchantByIdUser(int idUser){
        List<MsMerchant> a = getAllActive();
        MsMerchant o = null;
        for(MsMerchant b : a){
            if(b.getId_user() == idUser){
                o = b;
                break;
            }
        }
        return o;
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
