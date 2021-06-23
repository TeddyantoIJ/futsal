package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.DtFotolapangan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DtLapanganRepository extends CrudRepository <DtFotolapangan, Integer>{

    @Query("select * from dt_fotolapangan where id_lapangan =:id_lapangan")
    List<DtFotolapangan> findAllByIdLapangan(@Param("id_lapangan") int id_lapangan);

    @Query("select * from dt_fotolapangan where id_foto =:id_foto")
    DtFotolapangan findByIdFoto(@Param("id_foto") int id_foto);

}
