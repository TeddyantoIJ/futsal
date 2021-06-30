package ac.id.polman.astra.futsal.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class TrPendaftaranMerchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idMerchant;
    private int id_biaya;
    private int id_status;
    private int notifikasi;
    private int nominal;
    private String bukti_transfer;
    private String creaby;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creadate;
    private String modiby;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime modidate;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_merchant() {
        return idMerchant;
    }

    public void setId_merchant(int id_merchant) {
        this.idMerchant = id_merchant;
    }

    public int getId_biaya() {
        return id_biaya;
    }

    public void setId_biaya(int id_biaya) {
        this.id_biaya = id_biaya;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public int getNotifikasi() {
        return notifikasi;
    }

    public void setNotifikasi(int notifikasi) {
        this.notifikasi = notifikasi;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getBukti_transfer() {
        return bukti_transfer;
    }

    public void setBukti_transfer(String bukti_transfer) {
        this.bukti_transfer = bukti_transfer;
    }

    public String getCreaby() {
        return creaby;
    }

    public void setCreaby(String creaby) {
        this.creaby = creaby;
    }

    public String getCreadate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try{
            return creadate.format(formatter);
        }catch (Exception ex){
            return "";
        }

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
