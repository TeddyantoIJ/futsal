package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.model.MsMerchant;
import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import ac.id.polman.astra.futsal.model.TrPendaftaranMerchant;
import ac.id.polman.astra.futsal.repository.MerchantRepository;
import ac.id.polman.astra.futsal.repository.TrPendaftaranMerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Tr_Pendaftaran_Merchant_Service {
    @Autowired
    TrPendaftaranMerchantRepository trPendaftaranMerchantRepository;
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
            pendaftaranMerchant.setId(-1);
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
            if(list.get(i).getId_status() == 2){
                output.add(list.get(i));
            }
        }

        return output;
    }

    public int income(){
        List<TrPendaftaranMerchant> a = (List<TrPendaftaranMerchant>) trPendaftaranMerchantRepository.findAll();
        int output = 0;
        for(TrPendaftaranMerchant daf : a){
            if(daf.getId_status() == 3){
                output += daf.getNominal();
            }
        }
        return output;
    }

    public List<TrPendaftaranMerchant> getAllByDate(String tanggal1, String tanggal2){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<TrPendaftaranMerchant> a = trPendaftaranMerchantRepository.findAllByOrderByModidate();
        TrPendaftaranMerchant total = new TrPendaftaranMerchant();

        List<TrPendaftaranMerchant> c = new ArrayList<>();
        int total_uang = 0;
        for(TrPendaftaranMerchant b : a){
            try{
                Date modidate = sdf.parse(b.getModidate().toString());
                Date comp1 = sdf.parse(tanggal1);
                Date comp2 = sdf.parse(tanggal2);
                if(modidate.compareTo(comp1) >= 0 && modidate.compareTo(comp2) <= 0){
                    if(b.getId_status() == 3){
                        c.add(b);
                        total_uang += b.getNominal();
                    }
                }
            }catch (Exception e){
                System.out.println("Error pak");
            }
        }
        total.setNominal(total_uang);
        total.setId_status(-1);
        c.add(total);
        return c;
    }
}
