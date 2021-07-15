package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrBookingLapanganRepository extends CrudRepository<TrBookingLapangan, Integer> {
    List<TrBookingLapangan> findAllByIdTimOrderByTanggalAscJamAsc(int idTim);
    List<TrBookingLapangan> findAllByOrderByTanggalAscJamAsc();
}
