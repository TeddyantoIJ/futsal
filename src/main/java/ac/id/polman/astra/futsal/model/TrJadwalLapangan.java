package ac.id.polman.astra.futsal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
public class TrJadwalLapangan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idLapangan;
    private int idTim1;
    private int idTim2;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggal;
    @DateTimeFormat(pattern = "HH:mm")
    private Time jam;
    private int durasi;
    private String creaby;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creadate;
    private String modiby;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime modidate;
    private int status;
    private int mainBareng;

    public int getMainBareng() {
        return mainBareng;
    }

    public void setMainBareng(int mainBareng) {
        this.mainBareng = mainBareng;
    }

    public int getIdTim2() {
        return idTim2;
    }

    public void setIdTim2(int idTim2) {
        this.idTim2 = idTim2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(int idLapangan) {
        this.idLapangan = idLapangan;
    }

    public int getIdTim1() {
        return idTim1;
    }

    public void setIdTim1(int idTim1) {
        this.idTim1 = idTim1;
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

    public Date getDurasi() {
        String[] waktu = jam.toString().split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(waktu[0]));
        calendar.add(Calendar.HOUR_OF_DAY, +durasi);
        return calendar.getTime();
    }
    public int getDurasi1(){
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
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
