package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.TrReview;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<TrReview, Integer> {
    TrReview findById(int id);
}
