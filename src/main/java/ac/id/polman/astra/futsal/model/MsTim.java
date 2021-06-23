package ac.id.polman.astra.futsal.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class MsTim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTim;
    private Integer idUser;
    private String nama;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime tglBerdiri;
    private Integer privat;
    private Integer tipePemain;
    private String logo  ;
    private String banner;
    private String creaby;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creadate;
    private String modiby;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime modidate;
    private int status;

    public Integer getIdTim() {
        return idTim;
    }

    public void setIdTim(Integer idTim) {
        this.idTim = idTim;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public LocalDateTime getTglBerdiri() {
        return tglBerdiri;
    }

    public void setTglBerdiri(LocalDateTime tglBerdiri) {
        this.tglBerdiri = tglBerdiri;
    }

    public Integer getPrivat() {
        return privat;
    }

    public void setPrivat(Integer privat) {
        this.privat = privat;
    }

    public Integer getTipePemain() {
        return tipePemain;
    }

    public void setTipePemain(Integer tipePemain) {
        this.tipePemain = tipePemain;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getCreaby() {
        return creaby;
    }

    public void setCreaby(String creaby) {
        this.creaby = creaby;
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
