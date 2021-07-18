package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import ac.id.polman.astra.futsal.model.TrPelunasan;
import ac.id.polman.astra.futsal.repository.LapanganRepository;
import ac.id.polman.astra.futsal.repository.TrBookingLapanganRepository;
import ac.id.polman.astra.futsal.repository.TrPelunasanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Tr_pelunasan_service {
    @Autowired
    TrPelunasanRepository trPelunasanRepository;
    @Autowired
    TrBookingLapanganRepository trBookingLapanganRepository;
    @Autowired
    LapanganRepository lapanganRepository;

    public List<TrPelunasan> getAll(){
        List<TrPelunasan> a = (List<TrPelunasan>) trPelunasanRepository.findAll();
        return a;
    }
    public TrPelunasan getByIdBooking(int id){
        TrPelunasan pelunasan = trPelunasanRepository.findByIdTrbooking(id);
        return pelunasan;
    }

    public List<TrPelunasan> getAllByIdMerchant(int idMerchant){
        List<MsLapangan> lapangan = lapanganRepository.findAllByIdMerchant(idMerchant);
        List<TrBookingLapangan> booking = trBookingLapanganRepository.findAllByIdStatusOrderByTanggalAscJamAsc(8);
        List<TrPelunasan> pelunasan = new ArrayList<>();
        for(TrBookingLapangan book : booking){
            for(MsLapangan lap : lapangan){
                if(lap.getIdLapangan() == book.getId_lapangan()){
                    pelunasan.add(trPelunasanRepository.findByIdTrbooking(book.getId()));
                }
            }
        }
        return pelunasan;
    }
//    a========================
    public void save(TrPelunasan a){
        trPelunasanRepository.save(a);
    }
}
