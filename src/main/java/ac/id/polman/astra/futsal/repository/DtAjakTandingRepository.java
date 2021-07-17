package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.DtAjakTanding;
import ac.id.polman.astra.futsal.model.TrAjakTanding;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DtAjakTandingRepository extends CrudRepository<DtAjakTanding, Integer> {
    @Query("select * from dt_ajak_tanding where id=:id")
    DtAjakTanding findById(@Param("id") int id);

    @Query("select * from dt_ajak_tanding where id_ajak_tanding=:id_ajak")
    DtAjakTanding findByIdAjakTanding(@Param("id_ajak") int id_ajak);
}
