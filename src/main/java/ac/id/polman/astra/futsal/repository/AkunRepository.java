package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.MsAkun;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AkunRepository extends CrudRepository<MsAkun, Integer> {
    @Query("select * from ms_akun where id_akun =:id_akun")
    MsAkun findByIdAkun(@Param("id_akun") int id_akun);

    @Query("select * from ms_akun where id_role =:id_role")
    List<MsAkun> findAllByIdRole(@Param("id_role") int id_role);
}
