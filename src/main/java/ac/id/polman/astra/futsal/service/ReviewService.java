package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.TrReview;
import ac.id.polman.astra.futsal.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

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

//    ====================================
    public void save(TrReview a){
        reviewRepository.save(a);
    }
}
