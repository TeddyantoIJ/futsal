package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.TrAjakTanding;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AjakTandingRepository extends CrudRepository<TrAjakTanding, Integer> {
    @Query("select * from tr_ajak_tanding where id_tim1 =:idTim1")
    List<TrAjakTanding> findByIdTim1(@Param("idTim1") int idTim1);

    @Query("select * from tr_ajak_tanding where id=:id")
    TrAjakTanding findById(@Param("id") int id);
}
