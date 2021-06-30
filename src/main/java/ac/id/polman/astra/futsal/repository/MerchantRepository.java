package ac.id.polman.astra.futsal.repository;

import ac.id.polman.astra.futsal.model.MsMerchant;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MerchantRepository extends CrudRepository<MsMerchant, Integer> {


//    @Query(value = "INSERT INTO ms_merchant  Values (:nama,:alamat,:telephone,:narahubung,:deskripsi," +
//            ":rating,:foto,:banner,:creaby, :creadate,:modiby,:modidate,:status,:id_user)")
//    void saveMerchant(
//            @Param("nama") String nama,
//            @Param("alamat") String alamat,
//            @Param("telephone") String telephone ,
//            @Param("narahubung") String narahubung,
//            @Param("deskripsi") String deskripsi ,
//            @Param("rating") Float rating,
//            @Param("foto") String foto  ,
//            @Param("banner") String banner,
//            @Param("creaby") String creaby,
//            @Param("creadate") LocalDateTime creadate,
//            @Param("modiby") String modiby,
//            @Param("modidate") LocalDateTime modidate,
//            @Param("status") int status,
//            @Param("id_user") Integer id_user
//    );
//
//
//    @Query(value = "update ms_merchant set nama=:nama,alamat=:alamat,telephone=:telephone,narahubung=:narahubung,deskripsi=:deskripsi," +
//            "rating=:rating,foto=:foto,banner=:banner,creaby=:creaby, creadate=:creadate,modiby=:modiby,modidate=:modidate,status=:status,id_user=:id_user " +
//            "where id_merchant=:id_merchant")
//    void updateMerchant(
//            @Param("id_merchant") Integer id_merchant,
//            @Param("nama") String nama,
//            @Param("alamat") String alamat,
//            @Param("telephone") String telephone ,
//            @Param("narahubung") String narahubung,
//            @Param("deskripsi") String deskripsi ,
//            @Param("rating") Float rating,
//            @Param("foto") String foto  ,
//            @Param("banner") String banner,
//            @Param("creaby") String creaby,
//            @Param("creadate") LocalDateTime creadate,
//            @Param("modiby") String modiby,
//            @Param("modidate") LocalDateTime modidate,
//            @Param("status") int status,
//            @Param("id_user") Integer id_user
//    );
//
    @Query("select * from ms_merchant where id_merchant =:id_merchant")
    MsMerchant findByIdMerchant(@Param("id_merchant") int id_merchant);

    @Query("select * from ms_merchant where id_user =:id_user")
    MsMerchant findByIdUser(@Param("id_user") int id_user);
}
