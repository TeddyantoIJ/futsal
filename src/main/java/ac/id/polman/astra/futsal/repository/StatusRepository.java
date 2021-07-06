package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.MsStatus;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusRepository extends CrudRepository<MsStatus, Integer> {

    @Query("select * from ms_status where id_status =:id_status")
    MsStatus findByIdStatus(@Param("id_status") int id_status);

    @Query("select * from ms_status where status =:status")
    List<MsStatus> findAllByStatus(@Param("status") int status);
}
