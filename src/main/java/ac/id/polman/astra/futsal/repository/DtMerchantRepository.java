package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.DtMerchant;
import ac.id.polman.astra.futsal.model.MsMerchant;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DtMerchantRepository extends CrudRepository<DtMerchant, Integer> {

    @Query("select * from dt_merchant where id_dtmerchant =:id_dtmerchant")
    DtMerchant findByIdDtmerchant (@Param("id_dtmerchant") int id_dtmerchant);

    @Query("select * from dt_merchant where id_merchant =:id_merchant")
    List<DtMerchant> findByIdMerchant (@Param("id_merchant") int id_merchant);
}
