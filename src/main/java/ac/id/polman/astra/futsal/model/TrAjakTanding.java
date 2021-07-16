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
public class TrAjakTanding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idTim1;
    private int idTim2;
    private int id_lapangan;
    private int id_status;
    private int notifikasi;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggal;
    @DateTimeFormat(pattern = "HH:mm")
    private Time jam;
    private String alasan;
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

    public int getIdTim1() {
        return idTim1;
    }

    public void setIdTim1(int idTim1) {
        this.idTim1 = idTim1;
    }

    public int getIdTim2() {
        return idTim2;
    }

    public void setIdTim2(int idTim2) {
        this.idTim2 = idTim2;
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

    public String getAlasan() {
        return alasan;
    }

    public void setAlasann(String alasan) {
        this.alasan = alasan;
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
