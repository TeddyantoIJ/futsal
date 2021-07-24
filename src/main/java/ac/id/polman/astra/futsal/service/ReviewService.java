package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import ac.id.polman.astra.futsal.model.TrReview;
import ac.id.polman.astra.futsal.repository.DtLapanganRepository;
import ac.id.polman.astra.futsal.repository.LapanganRepository;
import ac.id.polman.astra.futsal.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    LapanganRepository lapanganRepository;

    MerchantService merchantService;

    public List<TrReview> getAll(){
        List<TrReview> a = (List<TrReview>) reviewRepository.findAll();
        return a;
    }

    public List<TrReview> getAllOrder(){
        List<TrReview> a = reviewRepository.findAllByOrderByCreadateDesc();
        return a;
    }

    public List<TrReview> getAllByIdMerchant(int id){
        List<TrReview> a = new ArrayList<>();
        for ( TrReview b : getAllOrder())
        {
            if(id == b.getIdMerchant()){
                a.add(b);
            }
        }
        return a;
    }

    public TrReview getByTanggalIdLapangan(TrBookingLapangan a){
        List<TrReview> reviews = getAllByIdMerchant(lapanganRepository.findByIdLapangan(a.getId_lapangan()).getIdMerchant());
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(TrReview review : reviews){
            try {
                if(review.getCreadate().compareTo(a.getModidate()) == 0){
                    return review;
                }
            }catch (Exception e){
                System.out.println("ERROR getByTanggalIdLapangan : " + e.getMessage());
            }

        }
        return null;
    }

    public TrReview getByIdBooking(int idBooking){
        List<TrReview> a = getAll();
        for(TrReview r : a){
            if(r.getId_trbooking() == idBooking){
                return r;
            }
        }
        return null;
    }

    public float getRatingByIdMerchant(int id){
        List<TrReview> reviews = getAllByIdMerchant(id);
        float rating = 0;
        int valid = 0;
        for(TrReview r : reviews){
            if(r.getStatus() == 1){
                rating = rating + r.getRating();
                valid++;
            }
        }
        try{
            rating = rating / valid;
        }catch (Exception e){
        }

        return  rating;
    }

    public TrReview getById(int id){
        TrReview trReview = reviewRepository.findById(id);
        return trReview;
    }
//    ====================================
    public void save(TrReview a){
        reviewRepository.save(a);
    }

    public void remove(TrReview a){
        reviewRepository.delete(a);
    }
}
