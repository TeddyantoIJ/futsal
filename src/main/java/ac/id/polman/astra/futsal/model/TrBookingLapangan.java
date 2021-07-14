package ac.id.polman.astra.futsal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class TrBookingLapangan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int id_tim;
    private int id_lapangan;
    private int id_status;
    private int notifikasi;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggal;
    @DateTimeFormat(pattern = "HH:mm")
    private Time jam;
    private int durasi;
    private int dp;
    private int sub_harga;
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

    public int getId_tim() {
        return id_tim;
    }

    public void setId_tim(int id_tim) {
        this.id_tim = id_tim;
    }

    public int getId_lapangan() {
        return id_lapangan;
    }

    public void setId_lapangan(int id_lapangan) {
        this.id_lapangan = id_lapangan;
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

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Time getJam() {
        return jam;
    }

    public void setJam(Time jam) {
        this.jam = jam;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public int getSub_harga() {
        return sub_harga;
    }

    public void setSub_harga(int sub_harga) {
        this.sub_harga = sub_harga;
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
