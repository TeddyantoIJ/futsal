package ac.id.polman.astra.futsal.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class DtMerchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDtmerchant;
    private Integer idMerchant;
    private Integer id_fasilitas;
    private Integer jumlah;
    private String creaby;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creadate;
    private String modiby;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime modidate;
    private int status;

    public String getCreaby() {
        return creaby;
    }

    public void setCreaby(String creaby) {
        this.creaby = creaby;
    }
    public Integer getId_dtmerchant() {
        return idDtmerchant;
    }

    public void setId_dtmerchant(Integer id_dtmerchant) {
        this.idDtmerchant = id_dtmerchant;
    }

    public Integer getId_merchant() {
        return idMerchant;
    }

    public void setId_merchant(Integer id_merchant) {
        this.idMerchant = id_merchant;
    }

    public Integer getId_fasilitas() {
        return id_fasilitas;
    }

    public void setId_fasilitas(Integer id_fasilitas) {
        this.id_fasilitas = id_fasilitas;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public LocalDateTime getCreadate() {
        return creadate;
    }

    public void setCreadate(LocalDateTime creadate) {
        this.creadate = creadate;
    }

    public String getModiby() {
        return modiby;
    }

    public void setModiby(String modiby) {
        this.modiby = modiby;
    }

    public LocalDateTime getModidate() {
        return modidate;
    }

    public void setModidate(LocalDateTime modidate) {
        this.modidate = modidate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
