package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import ac.id.polman.astra.futsal.model.TrReview;
import ac.id.polman.astra.futsal.repository.DtLapanganRepository;
import ac.id.polman.astra.futsal.repository.LapanganRepository;
import ac.id.polman.astra.futsal.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    LapanganRepository lapanganRepository;

    public List<TrReview> getAll(){
        List<TrReview> a = (List<TrReview>) reviewRepository.findAll();
        return a;
    }

    public List<TrReview> getAllByIdMerchant(int id){
        List<TrReview> a = getAll();
        for ( TrReview b:a)
        {
            if(id != b.getIdMerchant()){
                a.remove(b);
            }
        }
        return a;
    }

    public TrReview getByTanggalIdLapangan(TrBookingLapangan a){
        List<TrReview> reviews = getAllByIdMerchant(lapanganRepository.findByIdLapangan(a.getId_lapangan()).getIdMerchant());
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(TrReview review : reviews){
            try {
                System.out.println("Tanggal review : " + review.getCreadate());
                System.out.println("Tanggal booking : " + a.getModidate());



                if(review.getCreadate().compareTo(a.getModidate()) == 0){
                    return review;
                }
            }catch (Exception e){
                System.out.println("ERROR : " + e.getMessage());
            }

        }
        return null;
    }

//    ====================================
    public void save(TrReview a){
        reviewRepository.save(a);
    }
}
