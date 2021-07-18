package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.TrMainBareng;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainBarengRepository extends CrudRepository<TrMainBareng, Integer> {
    @Query("select * from tr_main_bareng where id_jadwal_lapangan =:id and status:=status")
    List<TrMainBareng> findByIdJadwalLapanganAndStatus(@Param("id") int id, @Param("status") int status);

    @Query("select * from tr_main_bareng where id=:id")
    TrMainBareng findById(@Param("id") int id);
}
