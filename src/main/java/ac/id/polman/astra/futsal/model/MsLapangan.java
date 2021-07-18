package ac.id.polman.astra.futsal.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class MsLapangan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLapangan;

    private Integer idMerchant;
    private String nama;
    private String tipe_lapangan;
    private long harga;
    private long harga_penerangan;
    private int panjang_lapangan;
    private int lebar_lapangan;
    private String creaby;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creadate;
    private String modiby;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime modidate;
    private int status;

    public Integer getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(Integer idLapangan) {
        this.idLapangan = idLapangan;
    }

    public Integer getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(Integer idMerchant) {
        this.idMerchant = idMerchant;
    }

    public String getCreaby() {
        return creaby;
    }

    public void setCreaby(String creaby) {
        this.creaby = creaby;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTipe_lapangan() {
        return tipe_lapangan;
    }

    public void setTipe_lapangan(String tipe_lapangan) {
        this.tipe_lapangan = tipe_lapangan;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public long getHarga_penerangan() {
        return harga_penerangan;
    }

    public void setHarga_penerangan(long harga_penerangan) {
        this.harga_penerangan = harga_penerangan;
    }

    public int getPanjang_lapangan() {
        return panjang_lapangan;
    }

    public void setPanjang_lapangan(int panjang_lapangan) {
        this.panjang_lapangan = panjang_lapangan;
    }

    public int getLebar_lapangan() {
        return lebar_lapangan;
    }

    public void setLebar_lapangan(int lebar_lapangan) {
        this.lebar_lapangan = lebar_lapangan;
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
