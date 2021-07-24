package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.MsLapangan;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LapanganRepository extends CrudRepository<MsLapangan, Integer> {
    @Query("select * from ms_lapangan where id_lapangan =:id_lapangan")
    MsLapangan findByIdLapangan(@Param("id_lapangan") int id_lapangan);

    @Query("select * from ms_lapangan where id_merchant =:id_merchant")
    List<MsLapangan> findAllByIdMerchant(@Param("id_merchant") int id_merchant);
    List<MsLapangan> findAllByIdMerchantOrderByModidateDesc(@Param("id_merchant") int id_merchant);
}
