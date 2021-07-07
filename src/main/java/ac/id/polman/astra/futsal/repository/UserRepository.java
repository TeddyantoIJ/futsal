package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.MsUser;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<MsUser, Integer> {
    @Query("select * from ms_user where id_user =:id_user")
    MsUser findByIdUser(@Param("id_user") int id_user);

    @Query("select * from ms_user where id_akun =:id_akun")
    MsUser findByIdAkun(@Param("id_akun") int id_akun);

    @Query("select * from ms_user where status =:status")
    List<MsUser> findAllByStatus(@Param("status") int status);
}
