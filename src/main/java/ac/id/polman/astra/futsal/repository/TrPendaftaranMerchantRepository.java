package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.TrPendaftaranMerchant;


import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrPendaftaranMerchantRepository extends CrudRepository<TrPendaftaranMerchant, Integer> {
    @Query("select * from tr_pendaftaran_merchant where id_merchant =:id_merchant")
    TrPendaftaranMerchant findByIdMerchant(@Param("id_merchant") int id_merchant);

    @Query("select * from tr_pendaftaran_merchant where id =:id")
    TrPendaftaranMerchant findById(@Param("id") int id);

}
