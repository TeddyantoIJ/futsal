package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.MsFasilitas;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FasilitasRepository extends CrudRepository<MsFasilitas, Integer> {
    @Query("select * from ms_fasilitas where id_fasilitas =:id_fasilitas")
    MsFasilitas findByIdFasilitas(@Param("id_fasilitas") int id_fasilitas);

    @Query("select * from ms_fasilitas where status =:status")
    List<MsFasilitas> findAllByStatus(@Param("status") int status);
}
