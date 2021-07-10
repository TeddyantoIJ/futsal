package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.MsMerchant;
import ac.id.polman.astra.futsal.model.TrJadwalLapangan;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrJadwalLapanganRepository extends CrudRepository<TrJadwalLapangan, Integer> {
    @Query("select * from tr_jadwal_lapangan where id_lapangan =:id_lapangan order by tanggal asc, jam asc")
    List<TrJadwalLapangan> findAllByIdLapanganOrderByTanggalAscJamAsc(@Param("id_lapangan") int id_lapangan);
}
