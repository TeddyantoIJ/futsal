package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.TrAjakTanding;
import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.DateTimeException;
import java.util.Date;
import java.util.List;

public interface AjakTandingRepository extends CrudRepository<TrAjakTanding, Integer> {
    @Query("select * from tr_ajak_tanding where id_tim1 =:idTim1")
    List<TrAjakTanding> findByIdTim1(@Param("idTim1") int idTim1);

    @Query("select * from tr_ajak_tanding where id_tim2 =:idTim2")
    List<TrAjakTanding> findByIdTim2(@Param("idTim2") int idTim2);

    @Query("select * from tr_ajak_tanding where id=:id")
    TrAjakTanding findById(@Param("id") int id);

    //mencari data terakhir, method alasan suapaya bisa normal
    @Query("SELECT TOP 1 * FROM tr_ajak_tanding ORDER BY id DESC")
    TrAjakTanding findTopByOrderByIdDesc();

    @Query("select * from tr_ajak_tanding where id_tim1=idtim and tanggal=tanggal and jam=jam")
    TrAjakTanding findByIdTim1AndTanggalAndJam(@Param("idtim") int idtim, @Param("tanggal") Date tanggal, @Param("jam") Time jam);

    List<TrAjakTanding> findAllByOrderByModidateAsc();
}
