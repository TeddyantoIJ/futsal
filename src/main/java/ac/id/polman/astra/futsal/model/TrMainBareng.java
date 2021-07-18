package ac.id.polman.astra.futsal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class TrMainBareng {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idTim;
    private int idUser;
    private int idStatus;
    private int notifikasi;
    private int idJadwalLapangan;
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

    public int getIdTim() {
        return idTim;
    }

    public void setIdTim(int idTim) {
        this.idTim = idTim;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public int getNotifikasi() {
        return notifikasi;
    }

    public void setNotifikasi(int notifikasi) {
        this.notifikasi = notifikasi;
    }

    public int getIdJadwalLapangan() {
        return idJadwalLapangan;
    }

    public void setIdJadwalLapangan(int idJadwalLapangan) {
        this.idJadwalLapangan = idJadwalLapangan;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
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
