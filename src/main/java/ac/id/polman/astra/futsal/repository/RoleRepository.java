package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.MsLapangan;
import ac.id.polman.astra.futsal.model.MsRole;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends CrudRepository<MsRole, Integer> {

    @Query("select * from ms_role where id_role =:id_role")
    MsRole findByIdRole(@Param("id_role") int id_role);
}
