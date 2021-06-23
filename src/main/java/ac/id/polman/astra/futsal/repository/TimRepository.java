package ac.id.polman.astra.futsal.repository;
import ac.id.polman.astra.futsal.model.MsTim;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TimRepository extends CrudRepository<MsTim, Integer> {
    @Query("select * from ms_tim where id_tim =:id_tim")
    MsTim findByIdTim(@Param("id_tim") int id_tim);
}
