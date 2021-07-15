package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.TrDaftarTim;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DaftarTimRepository extends CrudRepository<TrDaftarTim, Integer> {
    @Query("select * from tr_daftartim where id =:id")
    TrDaftarTim findAllById(@Param("id") int id);

    @Query("select * from dt_daftartim where id_tim =:id_tim ORDER BY id_status ASC")
    List<TrDaftarTim> findAllByIdTim(@Param("id_tim") int id_tim);
}
