package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import ac.id.polman.astra.futsal.repository.LapanganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LapanganService {
    @Autowired
    LapanganRepository lapanganRepository;

    public List<MsLapangan> getAllLapangan(){
        List<MsLapangan> msLapanganList = (List<MsLapangan>) lapanganRepository.findAll();
        return msLapanganList;
    }

    public List<MsLapangan> getAllLapanganByIdMerchant(int id_merchant){
        List<MsLapangan> msLapanganList = lapanganRepository.findAllByIdMerchant(id_merchant);
        return msLapanganList;
    }

    public MsLapangan getLapanganByIdLapangan(int id_lapangan){
        MsLapangan msLapangan = lapanganRepository.findByIdLapangan(id_lapangan);
        return msLapangan;
    }

    public List<MsLapangan> getReport(List<TrBookingLapangan> booking, int idMerchant){
        List<MsLapangan> lapangans = getAllLapanganByIdMerchant(idMerchant);

        List<MsLapangan> lapanganList = new ArrayList<>();

        for(MsLapangan lap : lapangans){
            int penggunaan = 0;

            for(TrBookingLapangan book : booking){
                if(book.getId_lapangan() == lap.getIdLapangan()){
                    penggunaan++;
                }
            }

            lap.setStatus(penggunaan);
            lapanganList.add(lap);
        }
        return lapanganList;
    }
    //===================================
    public void saveLapangan(MsLapangan msLapangan){
        lapanganRepository.save(msLapangan);
    }

}
