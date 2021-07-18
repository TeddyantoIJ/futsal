package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.TrJadwalLapangan;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface TrJadwalLapanganRepository extends CrudRepository<TrJadwalLapangan, Integer> {
    @Query("select * from tr_jadwal_lapangan where id_lapangan =:id_lapangan order by tanggal asc, jam asc")
    List<TrJadwalLapangan> findAllByIdLapanganOrderByTanggalAscJamAsc(@Param("id_lapangan") int id_lapangan);
    List<TrJadwalLapangan> findAllByIdTim1OrderByTanggalAscJamAsc(@Param("idTim1") int idTim1);
    TrJadwalLapangan findById(int id);
    TrJadwalLapangan findByTanggalAndJamAndIdLapangan(Date tanggal, Time jam, int idLapangan);

    @Query("select * from tr_jadwal_lapangan where id_tim1 =:id and id_tim2=:id2")
    List<TrJadwalLapangan> findByIdTim1AndIdTim2(@Param("id") int id, @Param("id2") int id2);
}
