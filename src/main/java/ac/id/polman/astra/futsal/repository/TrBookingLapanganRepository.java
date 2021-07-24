package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.MsTim;
import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrBookingLapanganRepository extends CrudRepository<TrBookingLapangan, Integer> {
    List<TrBookingLapangan> findAllByIdTimOrderByTanggalAscJamAsc(int idTim);
    List<TrBookingLapangan> findAllByOrderByTanggalAscJamAsc();
    List<TrBookingLapangan> findAllByIdStatusOrderByTanggalAscJamAsc(int idStatus);
    List<TrBookingLapangan> findAllByOrderByModidateAsc();
    TrBookingLapangan findById(int id);

}
